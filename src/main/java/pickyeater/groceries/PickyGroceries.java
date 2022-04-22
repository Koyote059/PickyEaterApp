package pickyeater.groceries;

import pickyeater.food.Ingredient;

import java.util.Set;

/**
 * @author Claudio Di Maio
 */

public class PickyGroceries implements Groceries{
    private Set<Ingredient> ingredients;

    public PickyGroceries(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public double getPrice() {
        double price = 0;
        for (Ingredient i : ingredients){
            price += i.getPrice();
        }
        return price;
    }

    @Override
    public GroceriesCheckList generateCheckList() {
        return new PickyGroceriesChecklist(ingredients);
    }
}
