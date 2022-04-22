package pickyeater.mealplan;

import pickyeater.food.Accumulator;
import pickyeater.food.Meal;
import pickyeater.food.Nutrients;
import pickyeater.food.PickyAccumulator;

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
        Accumulator accumulator = new PickyAccumulator();
        for(Meal meal: meals) accumulator.sumNutrients(meal.getNutrients());
        return accumulator.generateNutrients();
    }
}
