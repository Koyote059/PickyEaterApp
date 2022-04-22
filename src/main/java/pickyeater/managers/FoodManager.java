//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.managers;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;

import java.util.Optional;
import java.util.Set;

public interface FoodManager {
    void saveMeal(Meal meal);

    void saveIngredient(Ingredient ingredient);

    Set<Ingredient> getIngredientsThatStartWith(String name);

    Set<Ingredient> getIngredients();

    Optional<Meal> getMeal(String name);

    Optional<Ingredient> getIngredient(String name);

    Set<Meal> getMealsThatStartsWith(String name);

    Set<Meal> getMeals();
}
