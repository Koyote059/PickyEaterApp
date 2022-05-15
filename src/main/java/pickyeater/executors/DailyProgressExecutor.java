package pickyeater.executors;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.UserGoal;
import pickyeater.managers.EaterManager;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DailyProgressExecutor {

    private final DailyProgresses dailyProgresses;
    private final Nutrients requiredNutrients;
    private final EaterManager eaterManager;

    public DailyProgressExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        this.dailyProgresses = eaterManager.getUserManager().getUser().get().getDailyProgresses();
        this.requiredNutrients = eaterManager.getUserManager().getUser().get().getUserGoal().getRequiredNutrients();
    }

    public List<Meal> getEatenMeals(){
        return dailyProgresses.getEatenMeals();
    }

    public int getBurntCalories(){
        return dailyProgresses.getBurnedCalories();
    }

    public float getEatenCalories(){
        return dailyProgresses.getEatenNutrients().getCalories();
    }

    public float getEatenProteins(){
        return dailyProgresses.getEatenNutrients().getProteins();
    }

    public float getEatenCarbs(){
        return dailyProgresses.getEatenNutrients().getCarbs();
    }

    public float getEatenFats(){
        return dailyProgresses.getEatenNutrients().getFats();
    }

    public float getCaloriesToEat(){
        return requiredNutrients.getCalories();
    }

    public float getProteinsToEat(){
        return requiredNutrients.getProteins();
    }

    public float getCarbsToEat(){
        return requiredNutrients.getCarbs();
    }

    public float getFatsToEat(){
        return requiredNutrients.getFats();
    }

    public Object[] getAllMealsObj() {
        // TODO: Check / Delete comment
        List<Meal> mealList = getEatenMeals(); //ExecutorProvider.getDailyProgressExecutor().getEatenMeals();
        int tmpSize = mealList.size();
        Object objects[] = new Object[tmpSize];
        for (Iterator<Meal> it = mealList.iterator(); it.hasNext(); tmpSize--) {
            Meal meal = it.next();
            objects[tmpSize - 1] = meal.getName();
        }
        return objects;
    }
}
