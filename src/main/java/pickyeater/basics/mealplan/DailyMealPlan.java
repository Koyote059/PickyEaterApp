package pickyeater.basics.mealplan;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;

import java.util.List;

public interface DailyMealPlan {
    List<Meal> getMeals();
    Nutrients getNutrients();
}
