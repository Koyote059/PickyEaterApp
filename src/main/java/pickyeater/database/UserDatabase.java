package pickyeater.database;

import pickyeater.user.User;

import java.util.Optional;

public interface UserDatabase {

    Optional<User> loadUser();
    void saveUser(User user);

}
