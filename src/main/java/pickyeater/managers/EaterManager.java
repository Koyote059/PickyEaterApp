//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.managers;

import pickyeater.basics.user.User;

import java.util.Optional;

public interface EaterManager {
    void saveUser(User user);

    Optional<User> getUser();

    FoodManager getFoodManager();
}
