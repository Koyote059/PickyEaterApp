package pickyeater.executors.creators;

import pickyeater.basics.food.Ingredient;
import pickyeater.builders.IngredientBuilder;
import pickyeater.builders.MealBuilder;
import pickyeater.builders.PickyMealBuilder;
import pickyeater.basics.food.Meal;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;
import pickyeater.utils.MealQuantityConverter;

import java.util.Iterator;
import java.util.Set;

public class CreateMealExecutor {
    private final EaterManager eaterManager;

    public CreateMealExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public MealBuilder getMealBuilder() {
        return new PickyMealBuilder();
    }

    public IngredientSearcherExecutor getIngredientSearcher() {
        return new IngredientSearcherExecutor(this.eaterManager);
    }

    public void saveMeal(Meal meal) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        foodManager.saveMeal(meal);
    }

    public void appendToSet(Set<Ingredient> ingredientSet, String item){
        ingredientSet.add(ExecutorProvider.getIngredientSearcherExecutor().getIngredientByName(item).get());
    }

    public Object[] getAllSelectedIngredientsObj(Set<Ingredient> ingredientSet) {
        int tmpSize = ingredientSet.size();
        Object objects[] = new Object[tmpSize];
        for (Iterator<Ingredient> it = ingredientSet.iterator(); it.hasNext(); tmpSize--) {
            Ingredient ingredient = it.next();
            objects[tmpSize - 1] = ingredient.getName();
        }
        return objects;
    }

    public void addIngredients(MealBuilder mealBuilder, Set<Ingredient> ingredients){
        int tmpSize = ingredients.size();
        for (Iterator<Ingredient> it = ingredients.iterator(); it.hasNext(); tmpSize--) {
            mealBuilder.addIngredients(it.next());
        }
    }

    public MealQuantityConverter getMealQuantityConverter() {
        return new MealQuantityConverter();
    }

    public boolean existsMeal(String mealName) {
        FoodManager foodManager = eaterManager.getFoodManager();
        return foodManager.hasMeal(mealName);
    }
}
