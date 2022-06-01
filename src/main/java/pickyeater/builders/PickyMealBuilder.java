//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package pickyeater.builders;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.PickyMeal;

import java.util.*;

public class PickyMealBuilder implements MealBuilder {
    private Set<Ingredient> ingredients = new HashSet<>();
    private String name = null;

    public PickyMealBuilder() {
    }

    public PickyMealBuilder(Meal meal) {
        this.name = meal.getName();
        this.ingredients = meal.getIngredients();
    }

    public void addIngredients(Ingredient... ingredients) {
        this.ingredients.addAll(List.of(ingredients));
    }

    public Meal build() {
        if (this.name == null) {
            throw new MissingFormatArgumentException("Missing arguments for IngredientBuilder!");
        } else {
            return new PickyMeal(this.ingredients, this.name);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<Ingredient> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }
}
