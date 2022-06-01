package pickyeater.executors.searcher;

import pickyeater.basics.food.Meal;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;

import javax.swing.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class MealSearcherExecutor extends JFrame {
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

    public Object[] getAllMealsObj() {
        Set<Meal> mealSet = ExecutorProvider.getEaterManager().getFoodManager().getMeals();
        int tmpSize = mealSet.size();
        Object[] objects = new Object[tmpSize];
        for (Iterator<Meal> it = mealSet.iterator(); it.hasNext(); tmpSize--) {
            Meal meal = it.next();
            objects[tmpSize - 1] = meal.getName();
        }
        return objects;
    }

    public Optional<Meal> getMealByName(String mealName) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.getMeal(mealName);
    }

    public void createUIComponents() {
    }
}
