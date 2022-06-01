package pickyeater.executors.searcher;

import pickyeater.basics.food.Ingredient;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;

import java.util.Iterator;
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

    public Object[] getAllIngredientsObj() {
        Set<Ingredient> ingredientSet = ExecutorProvider.getEaterManager().getFoodManager().getIngredients();
        int tmpSize = ingredientSet.size();
        Object[] objects = new Object[tmpSize];
        for (Iterator<Ingredient> it = ingredientSet.iterator(); it.hasNext(); tmpSize--) {
            Ingredient ingredient = it.next();
            objects[tmpSize - 1] = ingredient.getName();
        }
        return objects;
    }

    public Optional<Ingredient> getIngredientByName(String ingredientName) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getIngredient(ingredientName);
    }

    public void deleteIngredient(Ingredient ingredient) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        foodManager.deleteIngredient(ingredient);
    }

    public void saveIngredient(Ingredient ingredient) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        foodManager.saveIngredient(ingredient);
    }

    public boolean isIngredientUsed(Ingredient ingredient) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.isIngredientUsed(ingredient);
    }
}
