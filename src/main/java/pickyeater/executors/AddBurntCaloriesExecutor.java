package pickyeater.executors;

import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

public class AddBurntCaloriesExecutor {
    private final EaterManager eaterManager;
    private final DailyProgresses dailyProgresses;
    private final User user;

    public AddBurntCaloriesExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        user = eaterManager.getUserManager().getUser().get();
        this.dailyProgresses = user.getDailyProgresses();
    }

    public void setBurntCalories(float calories) {
        UserManager userManager = eaterManager.getUserManager();
        dailyProgresses.addBurnedCalories(calories);
        userManager.saveUser(user);
    }
}
