package pickyeater.algorithms;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.mealplan.MealPlan;

import java.util.List;

public interface MealPlanGenerator {
    
    MealPlan generate(List<Meal> availableMeals, Nutrients requiredNutrients, int days, int mealsInADay);

}
