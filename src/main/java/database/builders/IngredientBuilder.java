//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.builders;

import pickyeater.food.Ingredient;
import pickyeater.food.Nutrients;
import pickyeater.food.Quantity;

public interface IngredientBuilder {
    void setPrice(double price);

    void setName(String name);

    void addTags(String... tags);

    void setNutrients(Nutrients nutrients);

    Ingredient build();

    void setQuantity(Quantity quantity);
}
