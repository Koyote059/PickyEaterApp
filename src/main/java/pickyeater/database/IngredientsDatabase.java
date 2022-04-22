package pickyeater.database;

import pickyeater.food.Ingredient;

import java.util.Optional;
import java.util.Set;

public interface IngredientsDatabase {
    void saveIngredient(Ingredient ingredient);
    Optional<Ingredient> loadIngredient(String ingredientName);
    boolean hasIngredient(String ingredientName);
    Set<Ingredient> loadEveryIngredient();
    Set<Ingredient> getIngredientsThatStartWith(String string);

}
