package pickyeater.basics.user;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;

import java.time.LocalDate;
import java.util.List;

public interface DailyProgresses {

    void addEatenMeal(Meal meal);
    void removeEatenMeal(Meal meal);
    Nutrients getEatenNutrients();
    List<Meal> getEatenMeals();
    void addBurnedCalories(float calories);
    int getBurnedCalories();
    LocalDate getDate();


}
