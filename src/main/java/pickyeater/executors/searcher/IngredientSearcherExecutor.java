package pickyeater.executors.searcher;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
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

    public Ingredient getIngredientWithName(String name) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        Set<Ingredient> ingredientSet = foodManager.getIngredientsThatStartWith(name);
        return ingredientSet.iterator().next();
    }

    public Set<Ingredient> getAllIngredients() {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getIngredients();
    }

    public Object[] getAllIngredientsObj() {
        ExecutorProvider executorProvider = new ExecutorProvider();
        Set<Ingredient> ingredientSet = executorProvider.getEaterManager().getFoodManager().getIngredients();
        int tmpSize = ingredientSet.size();
        Object objects[] = new Object[tmpSize];
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
}
