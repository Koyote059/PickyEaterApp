//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Quantity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MealBuilder {
    void setName(String name);

    void addIngredients(Ingredient... ingredients);

    Meal build();
    String getName();

    Set<Ingredient> getIngredients();
}
