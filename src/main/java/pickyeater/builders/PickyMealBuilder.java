//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.*;

import java.util.*;

public class PickyMealBuilder implements MealBuilder {
    private Set<Ingredient> ingredients = new HashSet<>();
    private String name = null;
    private Quantity quantity = new PickyQuantity(100.0);

    public PickyMealBuilder() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public void addIngredients(Ingredient... ingredients) {
        this.ingredients.addAll(List.of(ingredients));
    }

    public Meal build() {
        if (this.name == null | this.name == null | this.quantity == null) {
            throw new MissingFormatArgumentException("Missing arguments for IngredientBuilder!");
        } else {
            return new PickyMeal(this.ingredients, this.name, this.quantity);
        }
    }
}
