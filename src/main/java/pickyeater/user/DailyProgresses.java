package pickyeater.user;

import pickyeater.food.Meal;
import pickyeater.food.Nutrients;

import java.time.LocalDate;
import java.util.List;

public interface DailyProgresses {

    void addEatenMeal(Meal meal);
    void removeEatenMeal(Meal meal);
    Nutrients getEatenNutrients();
    List<Meal> getEatenMeals();
    void addBurnedCalories(int calories);
    int getBurnedCalories();
    LocalDate getDate();


}
