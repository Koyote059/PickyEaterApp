package pickyeater.executors;

import pickyeater.basics.food.Meal;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

public class AddEatenMealExecutor {
    private final EaterManager eaterManager;
    private final DailyProgresses dailyProgresses;

    public AddEatenMealExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        this.dailyProgresses = eaterManager.getUserManager().getUser().get().getDailyProgresses();
    }

    public void addEatenMeal(Meal eatenMeal) {
        UserManager userManager = eaterManager.getUserManager();
        dailyProgresses.addEatenMeal(eatenMeal);
        userManager.saveUser(userManager.getUser().get());
    }
}
