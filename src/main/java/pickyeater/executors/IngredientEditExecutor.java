package pickyeater.executors;

import pickyeater.basics.food.Ingredient;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;

public class IngredientEditExecutor {
    private final EaterManager eaterManager;

    public IngredientEditExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public void deleteIngredient(Ingredient ingredient) {
        FoodManager foodManager = eaterManager.getFoodManager();
        foodManager.deleteIngredient(ingredient);
    }
}
