
package pickyeater.managers;

import pickyeater.basics.user.User;
import pickyeater.database.UserDatabase;

import java.util.Optional;

public class PickyUserManager implements UserManager {
    private final UserDatabase userDatabase;
    private User user = null;
    public PickyUserManager(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void saveUser(User user) {
        this.userDatabase.saveUser(user);
        this.user = user;
    }

    public void deleteUser(User user) {
        this.userDatabase.deleteUser(user);
        this.user = user;
    }

    public Optional<User> getUser() {
        if(user != null){
            return Optional.of(this.user);
        } else {
            return userDatabase.loadUser();
        }
    }
}
