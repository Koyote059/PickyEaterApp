//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.executors;

import pickyeater.builders.PickyUserBuilder;
import pickyeater.builders.UserBuilder;
import pickyeater.managers.EaterManager;
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
