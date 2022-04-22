package pickyeater.user;

import pickyeater.food.Meal;

import java.util.List;

public interface MealPlanChecklist {

    void addEatenMeal(Meal meal);

    void removeEatenMeal(Meal meal);

    void removeMealToEat(Meal meal);

    List<Meal> getEatenMeals();

    List<Meal> getMealsToEat();
}
