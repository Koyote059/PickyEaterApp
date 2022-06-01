package pickyeater.database;

import pickyeater.database.SQLutils.SQLExecutorManager;

import java.sql.SQLException;

public class SQLPickyEaterDB implements PickyEatersDatabase {
    private final IngredientsDatabase ingredientsDatabase;
    private final MealsDatabase mealsDatabase;
    private final UserDatabase userDatabase;
    private final GroceriesDatabase groceriesDatabase;

    public SQLPickyEaterDB(String path) {
        try {
            SQLExecutorManager queryExecutor = new SQLExecutorManager(path);
            queryExecutor.createTables();
            this.ingredientsDatabase = new SQLIngredientsDB(queryExecutor);
            this.mealsDatabase = new SQLMealsDB(queryExecutor);
            this.userDatabase = new SQLUserDB(queryExecutor);
            this.groceriesDatabase = new SQLGroceriesDatabase(queryExecutor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IngredientsDatabase getIngredientsDatabase() {
        return ingredientsDatabase;
    }

    public MealsDatabase getMealsDatabase() {
        return mealsDatabase;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public GroceriesDatabase getGroceriesDatabase() {
        return groceriesDatabase;
    }
}
