package pickyeater.groceries;

import pickyeater.food.Ingredient;

import java.util.Set;

/**
 * @author Claudio Di Maio
 */

public interface GroceriesCheckList {

    Set<Ingredient> getNeededIngredients();
    Set<Ingredient> getMissingIngredients();
    Set<Ingredient> getTakenIngredients();

    void checkIngredient(Ingredient ingredient);
    void unCheckIngredient(Ingredient ingredient);
    void setIngredientMissing(Ingredient ingredient);
    void unSetIngredientMissing(Ingredient ingredient);
}
