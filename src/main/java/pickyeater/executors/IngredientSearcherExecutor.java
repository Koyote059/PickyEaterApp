package pickyeater.executors;

import pickyeater.basics.food.Ingredient;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;

import java.util.Optional;
import java.util.Set;

public class IngredientSearcherExecutor {
    private final EaterManager eaterManager;

    public IngredientSearcherExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public Set<Ingredient> getIngredientsThatStartWith(String name) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getIngredientsThatStartWith(name);
    }

    public Set<Ingredient> getAllIngredients() {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getIngredients();
    }

    public Optional<Ingredient> getIngredientByName(String ingredientName) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getIngredient(ingredientName);
    }
}
