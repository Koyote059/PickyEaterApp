//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.Quantity;

public interface IngredientBuilder {
    void setPrice(float price);

    void setName(String name);

    void addTags(String... tags);

    void setNutrients(Nutrients nutrients);

    Ingredient build();

    void setQuantity(Quantity quantity);
}
