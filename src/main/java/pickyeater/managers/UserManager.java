/**
 * @author ZiCli
 */
package pickyeater.managers;

import pickyeater.basics.user.User;
import pickyeater.database.MealsDatabase;
import pickyeater.database.UserDatabase;

import java.util.Optional;

public interface UserManager {
    void saveUser(User user);

    Optional<User> getUser();
}
