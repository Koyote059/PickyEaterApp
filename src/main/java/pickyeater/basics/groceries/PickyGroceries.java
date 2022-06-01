package pickyeater.basics.groceries;

import pickyeater.basics.food.Ingredient;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Claudio Di Maio
 */
public class PickyGroceries implements Groceries {
    private final Set<Ingredient> ingredients;
    private final GroceriesCheckList checkList;

    public PickyGroceries(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.checkList = new PickyGroceriesChecklist(ingredients);
    }

    public PickyGroceries(Set<Ingredient> neededIngredients, Set<Ingredient> missingIngredients, Set<Ingredient> takenIngredients) {
        this.checkList = new PickyGroceriesChecklist(neededIngredients, missingIngredients, takenIngredients);
        this.ingredients = new HashSet<>(neededIngredients);
        this.ingredients.addAll(missingIngredients);
        this.ingredients.addAll(takenIngredients);
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public double getPrice() {
        double price = 0;
        for (Ingredient i : ingredients) {
            price += i.getPrice();
        }
        return price;
    }

    @Override
    public GroceriesCheckList generateCheckList() {
        return checkList;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return "PickyGroceries{" + "ingredients=" + ingredients + '}' + '\n';
    }
}
