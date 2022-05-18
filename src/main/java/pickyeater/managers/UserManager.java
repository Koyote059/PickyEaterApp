/**
 * @author ZiCli
 */
package pickyeater.managers;

import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.basics.user.User;
import pickyeater.database.MealsDatabase;
import pickyeater.database.UserDatabase;

import java.util.Optional;

public interface UserManager {
    void saveUser(User user);

    Optional<User> getUser();

    void saveGroceries(GroceriesCheckList groceriesCheckList);

    Optional<Groceries> getGroceries();

    void deleteGroceries(Groceries groceries);

    void deleteMealPlan(User user);
}
