
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

    private Groceries groceries = null;

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
        if(user == null){
            Optional<User> userOptional = userDatabase.loadUser();
            userOptional.ifPresent(value -> user = value);
        }
        return Optional.ofNullable(this.user);
    }

    @Override
    public void saveGroceries(GroceriesCheckList groceriesCheckList) {
        groceriesDatabase.saveGroceries(groceriesCheckList);
    }

    @Override
    public Optional<Groceries> getGroceries() {
        if(groceries==null){
            Optional<Groceries> groceriesOptional = groceriesDatabase.getGroceries();
            groceriesOptional.ifPresent(value -> groceries = value);
        }
        return Optional.ofNullable(groceries);
    }

    @Override
    public void deleteGroceries(Groceries groceries) {
        groceriesDatabase.deleteGroceries(groceries);

        this.groceries = null;
    }

    @Override
    public void deleteMealPlan(User user) {
        userDatabase.deleteMealPlan(user);
        user.setMealPlan(null);
    }
}
