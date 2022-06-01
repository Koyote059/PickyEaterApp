package pickyeater.database;

import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;

import java.util.Optional;

public interface GroceriesDatabase {
    Optional<Groceries> getGroceries();
    void saveGroceries(GroceriesCheckList groceriesCheckList);
    void deleteGroceries(Groceries groceries);
}
