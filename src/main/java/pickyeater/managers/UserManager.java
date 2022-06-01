/**
 *
 */
package pickyeater.managers;

import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.basics.user.User;

import java.util.Optional;

public interface UserManager {
    void saveUser(User user);
    void deleteUser(User user);
    Optional<User> getUser();
    void saveGroceries(GroceriesCheckList groceriesCheckList);
    Optional<Groceries> getGroceries();
    void deleteGroceries(Groceries groceries);
    void deleteMealPlan(User user);
}
