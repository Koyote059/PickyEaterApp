package pickyeater.algorithms;

import pickyeater.basics.food.*;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.builders.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomMealPlanGenerator implements MealPlanGenerator {
    @Override
    public MealPlan generate(Collection<Meal> availableMeals, Nutrients requiredNutrients, int days, int mealsInADay) {
        MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();
        mealPlanBuilder.setBeginningDay(LocalDate.now());


        for(int i=0; i<days; i++){
            List<Meal> meals = new ArrayList<>(mealsInADay);
            for(int j=0; j<mealsInADay; j++){
                Meal meal = getRandomMeal(new ArrayList<>(availableMeals));
                if(meals.contains(meal)){
                    j--;
                    continue;
                }
                meals.add(meal);
            }
            DailyMealPlanBuilder dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();
            List<Meal> normalizeMeals = normalizeMeals(requiredNutrients,meals,mealsInADay);
            normalizeMeals.forEach(dailyMealPlanBuilder::addMeal);
            mealPlanBuilder.addDailyMealPlan(dailyMealPlanBuilder.build());
        }


        return mealPlanBuilder.build();
    }

    private Meal getRandomMeal(List<Meal> availableMeals){
        Random random = new Random();
        return availableMeals.get(random.nextInt(availableMeals.size()));
    }

    private List<Meal> normalizeMeals(Nutrients requiredNutrients, List<Meal> meals, int mealsInADay){
        List<Meal> normalizedMeals = new ArrayList<>(mealsInADay);
        for (Meal meal : meals) {
            float mealCalories = meal.getNutrients().getCalories();
            float dailyRequiredCalories = requiredNutrients.getCalories();
            float requiredCaloriesFromMeal = dailyRequiredCalories / mealsInADay;
            float mealCaloriesRatio = requiredCaloriesFromMeal / mealCalories;
            MealBuilder mealBuilder = new PickyMealBuilder();
            mealBuilder.setName(meal.getName());
            for (Ingredient ingredient : meal.getIngredients()) {
                IngredientBuilder ingredientBuilder = new PickyIngredientBuilder(ingredient);
                Quantity ingredientQuantity = ingredient.getQuantity();
                ingredientBuilder.setQuantity(new PickyQuantity(
                        ingredientQuantity.getAmount()*mealCaloriesRatio,
                        ingredientQuantity.getQuantityType(),
                        ingredientQuantity.getGramsPerQuantity()
                ));

                NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
                Nutrients ingredientNutrients = ingredient.getNutrients();
                nutrientsBuilder.setComplexCarbs(ingredientNutrients.getComplexCarbs()*mealCaloriesRatio);
                nutrientsBuilder.setSimpleCarbs(ingredientNutrients.getSimpleCarbs()*mealCaloriesRatio);
                nutrientsBuilder.setFibers(ingredientNutrients.getFibers()*mealCaloriesRatio);
                nutrientsBuilder.setSaturatedFats(ingredientNutrients.getSaturatedFats()*mealCaloriesRatio);
                nutrientsBuilder.setUnSaturatedFats(ingredientNutrients.getUnSaturatedFats()*mealCaloriesRatio);
                nutrientsBuilder.setTransFats(ingredientNutrients.getTransFats()*mealCaloriesRatio);
                nutrientsBuilder.setProteins(ingredientNutrients.getProteins()*mealCaloriesRatio);
                nutrientsBuilder.setAlcohol(ingredientNutrients.getAlcohol()*mealCaloriesRatio);

                ingredientBuilder.setNutrients(nutrientsBuilder.build());
                mealBuilder.addIngredients(ingredientBuilder.build());
            }
            normalizedMeals.add(mealBuilder.build());
        }
        return normalizedMeals;
    }
}
