//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.food.Ingredient;
import pickyeater.food.Meal;
import pickyeater.food.Quantity;

public interface MealBuilder {
    void setName(String name);

    void setQuantity(Quantity quantity);

    void addIngredients(Ingredient... ingredients);

    Meal build();
}
