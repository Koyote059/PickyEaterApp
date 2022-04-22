//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.executors;

import database.builders.PickyUserBuilder;
import database.builders.UserBuilder;
import database.managers.EaterManager;
import pickyeater.user.User;

public class RegisterExecutor {
    private final EaterManager eaterManager;

    public RegisterExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public UserBuilder getUserBuilder() {
        return new PickyUserBuilder();
    }

    public void saveUser(User user) {
        this.eaterManager.saveUser(user);
    }
}
