//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Quantity;

public interface MealBuilder {
    void setName(String name);

    void addIngredients(Ingredient... ingredients);

    Meal build();
    String getName();
}
