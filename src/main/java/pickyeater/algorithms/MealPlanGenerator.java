package pickyeater.algorithms;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.mealplan.MealPlan;

import java.util.Collection;
import java.util.List;

public interface MealPlanGenerator {
    
    MealPlan generate(Collection<Meal> availableMeals, Nutrients requiredNutrients, int days, int mealsInADay);

}
