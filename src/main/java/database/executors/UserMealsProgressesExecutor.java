//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.executors;

import database.managers.EaterManager;
import pickyeater.user.DailyProgresses;
import pickyeater.user.User;

import java.util.Optional;

public class UserMealsProgressesExecutor {
    private final EaterManager eaterManager;
    private final User user;

    public UserMealsProgressesExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        Optional<User> userOptional = eaterManager.getUser();
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
