
package pickyeater.managers;

import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.basics.user.User;
import pickyeater.database.GroceriesDatabase;
import pickyeater.database.UserDatabase;

import java.util.Optional;

public class PickyUserManager implements UserManager {
    private final UserDatabase userDatabase;
    private final GroceriesDatabase groceriesDatabase;
    private User user = null;
    public PickyUserManager(UserDatabase userDatabase, GroceriesDatabase groceriesDatabase) {
        this.userDatabase = userDatabase;
        this.groceriesDatabase = groceriesDatabase;
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

    @Override
    public void saveGroceries(GroceriesCheckList groceriesCheckList) {
        groceriesDatabase.saveGroceries(groceriesCheckList);
    }

    @Override
    public Optional<Groceries> getGroceries() {
        return groceriesDatabase.getGroceries();
    }

    @Override
    public void deleteGroceries(Groceries groceries) {
        groceriesDatabase.deleteGroceries(groceries);
    }

    @Override
    public void deleteMealPlan(User user) {
        userDatabase.deleteMealPlan(user);
    }
}
