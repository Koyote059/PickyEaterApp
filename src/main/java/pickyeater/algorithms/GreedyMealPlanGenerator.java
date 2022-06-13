package pickyeater.algorithms;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.builders.DailyMealPlanBuilder;
import pickyeater.builders.MealPlanBuilder;
import pickyeater.builders.PickyDailyMealPlanBuilder;
import pickyeater.builders.PickyMealPlanBuilder;
import pickyeater.utils.MealQuantityConverter;

import java.time.LocalDate;
import java.util.*;

public class GreedyMealPlanGenerator implements MealPlanGenerator {
    private ArrayList<Meal> availableMeals;

    @Override
    public MealPlan generate(Collection<Meal> availableMeals, Nutrients requiredNutrients, int days, int mealsInADay) {
        if (availableMeals.size() < mealsInADay || days <= 0 || mealsInADay <= 0)
            throw new IllegalArgumentException();
        MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();
        this.availableMeals = new ArrayList<>(availableMeals);
        mealPlanBuilder.setBeginningDay(LocalDate.now());
        float maxMealWeight = 250 * (requiredNutrients.getCalories() / 2000);
        float idealCaloriesPerMeal = requiredNutrients.getCalories() / mealsInADay;
        for (int i = 0; i < days; i++) {
            DailyMealPlanBuilder dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();
            int randomMealsQuantity;
            if (availableMeals.size() > mealsInADay * 4) {
                randomMealsQuantity = mealsInADay * 4;
            } else if (availableMeals.size() > mealsInADay * 3) {
                randomMealsQuantity = mealsInADay * 3;
            } else if (availableMeals.size() > mealsInADay * 2) {
                randomMealsQuantity = mealsInADay * 2;
            } else {
                randomMealsQuantity = availableMeals.size();
            }
            List<Meal> randomMeals = getRandomMeals(randomMealsQuantity);
            List<Meal> sortedMeals = greedySort(randomMeals, maxMealWeight, idealCaloriesPerMeal);
            for (int j = 0; j < mealsInADay; j++) {
                dailyMealPlanBuilder.addMeal(sortedMeals.get(j));
            }
            mealPlanBuilder.addDailyMealPlan(dailyMealPlanBuilder.build());
        }
        return mealPlanBuilder.build();
    }

    private List<Meal> getRandomMeals(int count) {
        Set<Meal> meals = new HashSet<>();
        Random random = new Random();
        while (meals.size() < count) {
            meals.add(availableMeals.get(random.nextInt(availableMeals.size())));
        }
        return new ArrayList<>(meals);
    }

    private List<Meal> greedySort(List<Meal> randomMeals, float maxMealWeigh, float idealCaloriesPerMeal) {
        List<Meal> normalizedMeals = new ArrayList<>();
        for (Meal meal : randomMeals) {
            // Converts meal weight if they're over the limit
            MealQuantityConverter converter = new MealQuantityConverter();
            if (meal.getWeight() > maxMealWeigh)
                normalizedMeals.add(converter.convert(meal, maxMealWeigh));
            else
                normalizedMeals.add(meal);
        }
        normalizedMeals.sort((m1, m2) -> {
            float m1Calories = m1.getNutrients().getCalories();
            float m2Calories = m2.getNutrients().getCalories();
            // Ratio: index that indicates which one is closer to the idealCaloriesPerMeal
            float m1CaloriesRatio = m1Calories > idealCaloriesPerMeal ? m1Calories / idealCaloriesPerMeal : idealCaloriesPerMeal / m1Calories;
            float m2CaloriesRatio = m2Calories > idealCaloriesPerMeal ? m2Calories / idealCaloriesPerMeal : idealCaloriesPerMeal / m2Calories;
            return (int) (m1CaloriesRatio - m2CaloriesRatio);
        });
        return normalizedMeals;
    }
}
