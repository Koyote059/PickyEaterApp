package pickyeater.mealplan;

import pickyeater.food.Meal;
import pickyeater.food.Nutrients;

import java.util.List;

public interface DailyMealPlan {

    List<Meal> getMeals();
    Nutrients getNutrients();

}
