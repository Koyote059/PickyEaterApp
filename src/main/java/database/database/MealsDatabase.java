package database.database;

import pickyeater.food.Meal;

import java.util.Optional;
import java.util.Set;

public interface MealsDatabase {
    void saveMeal(Meal meal);
    Optional<Meal> loadMeal(String mealName);
    boolean hasMeal(String mealName);
    Set<Meal> loadEveryMeal();
    Set<Meal> getMealsThatStartWith(String string);
}
