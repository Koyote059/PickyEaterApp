package pickyeater.executors;

import pickyeater.basics.food.Meal;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;

import java.util.Set;

public class MealChooserExecutor {
    private final EaterManager eaterManager;

    public MealChooserExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public Set<Meal> getMealsThatStartWith(String name) {
        FoodManager foodManager = eaterManager.getFoodManager();
        return foodManager.getMealsThatStartsWith(name);
    }

    public Set<Meal> getEveryMeal() {
        FoodManager foodManager = eaterManager.getFoodManager();
        return foodManager.getMeals();
    }

    public void deleteMeal(Meal meal) {
        FoodManager foodManager = eaterManager.getFoodManager();
        foodManager.deleteMeal(meal);
    }

    public boolean isMealUsed(Meal meal) {
        FoodManager foodManager = eaterManager.getFoodManager();
        return foodManager.isMealUsed(meal);
    }
}
