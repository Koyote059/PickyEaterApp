//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.*;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class PickyIngredientBuilder implements IngredientBuilder {
    private double price = 0.0;
    private String name = null;
    private List<String> tags = new ArrayList<>();
    private Quantity quantity = null;
    private Nutrients nutrients = null;

    public PickyIngredientBuilder() {
    }

    public PickyIngredientBuilder(Ingredient ingredient) {
        this.price = ingredient.getPrice();
        this.name = ingredient.getName();
        this.tags = new ArrayList<>(ingredient.getTags());
        Quantity ingredientQuantity = ingredient.getQuantity();
        this.quantity = new PickyQuantity(ingredientQuantity.getQuantity(), ingredientQuantity.getQuantityType(), ingredientQuantity.getQuantityGrams());
        this.nutrients = ingredient.getNutrients();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTags(String... tags) {
        this.tags.addAll(List.of(tags));
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public Ingredient build() {
        if (this.name != null && this.quantity != null && this.nutrients != null) {
            return new PickyIngredient(this.nutrients, this.name, this.price, this.quantity, this.tags);
        } else {
            throw new MissingFormatArgumentException("Missing arguments for IngredientBuilder!");
        }
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
