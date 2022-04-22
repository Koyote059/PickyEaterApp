package pickyeater.food;

import java.util.List;
import java.util.Set;

/**
 * @author Claudio Di Maio
 */

public class PickyMeal implements Meal{
    private Set<Ingredient> ingredients;
    private String name;
    private Quantity quantity;
    private List<String> tags;

    public PickyMeal(Set<Ingredient> ingredients, String name, Quantity quantity, List<String> tags) {
        this.ingredients = ingredients;
        this.name = name;
        this.quantity = quantity;
        this.tags = tags;
    }

    @Override
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    /**
     * getNutrients: sum of all the Nutrients from the set of Ingredients.
     */
    @Override
    public Nutrients getNutrients() {
        Set<Ingredient> ingredientSet = getIngredients();
        Accumulator accumulator = new PickyAccumulator();
        for (Ingredient i : ingredientSet) {
            accumulator.sumNutrients(i.getNutrients());
        }
        return accumulator.generateNutrients();
    }

    @Override
    public String toString() {
        return "PickyMeal{" +
                "ingredients=" + ingredients +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", tags=" + tags +
                '}';
    }
}