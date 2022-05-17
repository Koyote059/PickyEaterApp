package pickyeater.executors;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;
import pickyeater.basics.user.UserGoal;
import pickyeater.executors.searcher.MealSearcherExecutor;
import pickyeater.managers.EaterManager;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DailyProgressExecutor {
    private final EaterManager eaterManager;
    private final User user;

    public DailyProgressExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        Optional<User> userOptional = eaterManager.getUserManager().getUser();
        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        } else {
            this.user = userOptional.get();
        }
    }

    public DailyProgresses getUserDailyProgresses() {
        return this.user.getDailyProgresses();
    }

    public MealSearcherExecutor getMealSearcher() {
        return new MealSearcherExecutor(this.eaterManager);
    }

    public void save() {
        this.eaterManager.getUserManager().saveUser(this.user);
    }

    public List<Meal> getEatenMeals(){
        return user.getDailyProgresses().getEatenMeals();
    }

    public int getBurntCalories(){
        return user.getDailyProgresses().getBurnedCalories();
    }

    public float getEatenCalories(){
        return user.getDailyProgresses().getEatenNutrients().getCalories();
    }

    public float getEatenProteins(){
        return user.getDailyProgresses().getEatenNutrients().getProteins();
    }

    public float getEatenCarbs(){
        return user.getDailyProgresses().getEatenNutrients().getCarbs();
    }

    public float getEatenFats(){
        return user.getDailyProgresses().getEatenNutrients().getFats();
    }

    public float getCaloriesToEat(){
        return user.getUserGoal().getRequiredNutrients().getCalories();
    }

    public float getProteinsToEat(){
        return user.getUserGoal().getRequiredNutrients().getProteins();
    }

    public float getCarbsToEat(){
        return user.getUserGoal().getRequiredNutrients().getCarbs();
    }

    public float getFatsToEat(){
        return user.getUserGoal().getRequiredNutrients().getFats();
    }

    public Object[] getAllMealsObj() {
        // TODO: Check / Delete comment
        List<Meal> mealList = getEatenMeals(); //ExecutorProvider.getDailyProgressExecutor().getEatenMeals();
        int tmpSize = mealList.size();
        Object[] objects = new Object[tmpSize];
        for (Iterator<Meal> it = mealList.iterator(); it.hasNext(); tmpSize--) {
            Meal meal = it.next();
            objects[tmpSize - 1] = meal.getName();
        }
        return objects;
    }
}
