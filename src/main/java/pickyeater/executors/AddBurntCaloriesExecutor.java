package pickyeater.executors;

import pickyeater.basics.food.Nutrients;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.managers.EaterManager;

public class AddBurntCaloriesExecutor {

    private final EaterManager eaterManager;

    private final DailyProgresses dailyProgresses;

    public AddBurntCaloriesExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        this.dailyProgresses = eaterManager.getUserManager().getUser().get().getDailyProgresses();
    }

    public void setBurntCalories(float calories){
        dailyProgresses.addBurnedCalories(calories);
    }
}
