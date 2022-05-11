package pickyeater.database;

import pickyeater.basics.groceries.Groceries;

import java.util.Optional;

public interface GroceriesDatabase {

    Optional<Groceries> getGroceries();

    void saveGroceries(Groceries groceries);

    void deleteGroceries(Groceries groceries);
}
