//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package pickyeater.builders;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;

import java.util.Set;

public interface MealBuilder {
    void addIngredients(Ingredient... ingredients);
    Meal build();
    String getName();
    void setName(String name);
    Set<Ingredient> getIngredients();
    void remove(Ingredient selectedIngredient);
}
