package pickyeater.executors;

import pickyeater.managers.EaterManager;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;

import java.util.Optional;

public class UserMealsProgressesExecutor {
    private final EaterManager eaterManager;
    private final User user;

    public UserMealsProgressesExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        Optional<User> userOptional = eaterManager.getUser(); //eaterManager.getUserManager().getUser();
        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        } else {
            this.user = (User)userOptional.get();
        }
    }

    public DailyProgresses getUserDailyProgresses() {
        return this.user.getDailyProgresses();
    }

    public MealSearcherExecutor getMealSearcher() {
        return new MealSearcherExecutor(this.eaterManager);
    }

    public void save() {
        this.eaterManager.saveUser(this.user);
    }
}
