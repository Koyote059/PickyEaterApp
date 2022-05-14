package pickyeater.executors;

import pickyeater.builders.MealBuilder;
import pickyeater.builders.PickyMealBuilder;
import pickyeater.basics.food.Meal;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;

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
}
