package pickyeater.basics.mealplan;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.NutrientsAccumulator;
import pickyeater.basics.food.PickyNutrientsAccumulator;

import java.util.Collections;
import java.util.List;

public class PickyDailyMealPlan implements DailyMealPlan {

    private final List<Meal> meals;

    public PickyDailyMealPlan(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }

    @Override
    public Nutrients getNutrients() {
        NutrientsAccumulator accumulator = new PickyNutrientsAccumulator();
        for(Meal meal: meals) accumulator.sumNutrients(meal.getNutrients());
        return accumulator.generateNutrients();
    }
}
