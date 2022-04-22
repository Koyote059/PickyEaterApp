//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.executors;

import pickyeater.food.Meal;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;

import java.util.Optional;
import java.util.Set;

public class MealSearcherExecutor {
    private final EaterManager eaterManager;

    public MealSearcherExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public Set<Meal> getMealsThatStartWith(String name) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getMealsThatStartsWith(name);
    }

    public Set<Meal> getAllMeals() {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getMeals();
    }

    public Optional<Meal> getMealByName(String mealName) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getMeal(mealName);
    }
}
