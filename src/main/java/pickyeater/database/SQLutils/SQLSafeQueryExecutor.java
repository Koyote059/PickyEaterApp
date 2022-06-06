package pickyeater.database.SQLutils;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSafeQueryExecutor {
    public ResultSet selectFromUserTable() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM User");
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    private CachedRowSet cache(ResultSet resultSet) throws SQLException {
        CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
        if (!resultSet.isBeforeFirst()) {
            resultSet.close();
            return cachedRowSet;
        }
        cachedRowSet.populate(resultSet);
        return cachedRowSet;
    }

    public ResultSet selectIngredient(String ingredientName) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT * FROM Ingredients WHERE ingredientName = '%s'", ingredientName));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectMeal(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            String query = String.format("\n" + "SELECT Meals.mealName AS mealName,\n" + "       Ingredients.ingredientName AS ingredientName,\n" + "       quantityType,\n" + "       gramsPerQuantity,\n" + "       MealCompositions.quantity AS quantity,\n" + "       price * ( MealCompositions.quantity / 100) AS price,\n" + "       complexCarbs * ( MealCompositions.quantity / 100) AS complexCarbs,\n" + "       simpleCarbs * ( MealCompositions.quantity / 100) AS simpleCarbs,\n" + "       fibers * ( MealCompositions.quantity / 100) AS fibers,\n" + "       saturatedFats * ( MealCompositions.quantity / 100) AS saturatedFats,\n" + "       unSaturatedFats * ( MealCompositions.quantity / 100) AS unSaturatedFats,\n" + "       transFats * ( MealCompositions.quantity / 100) AS transFats,\n" + "       proteins * ( MealCompositions.quantity / 100) AS proteins,\n" + "       alcohol * ( MealCompositions.quantity / 100) AS alcohol\n" + "    FROM Meals LEFT JOIN MealCompositions ON Meals.mealName = MealCompositions.mealName\n" + "    LEFT JOIN Ingredients ON MealCompositions.ingredientName = Ingredients.ingredientName\n" + "WHERE Meals.mealName = '%s'", mealName);
            resultSet = statement.executeQuery(query);
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectFromEatenMealsTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT MealCompositions.mealName AS mealName,\n" + "       Ingredients.ingredientName AS ingredientName,\n" + "       MealCompositions.quantity * ingredientsRatio AS quantity,\n" + "       quantityType,\n" + "       ingredientsRatio,\n" + "       gramsPerQuantity,\n" + "       mealNumber,\n" +
                    "       price * ( MealCompositions.quantity * ingredientsRatio/ 100) AS price,\n" + "       complexCarbs * ( MealCompositions.quantity * ingredientsRatio/ 100) AS complexCarbs,\n" + "       simpleCarbs * ( MealCompositions.quantity * ingredientsRatio/ 100) AS simpleCarbs,\n" + "       fibers * ( MealCompositions.quantity * ingredientsRatio/ 100) AS fibers,\n" + "       saturatedFats * ( MealCompositions.quantity * ingredientsRatio/ 100) AS saturatedFats,\n" + "       unSaturatedFats * ( MealCompositions.quantity *ingredientsRatio/ 100) AS unSaturatedFats,\n" + "       transFats * ( MealCompositions.quantity * ingredientsRatio/ 100) AS transFats,\n" + "       proteins * ( MealCompositions.quantity * ingredientsRatio/ 100) AS proteins,\n" + "       alcohol * ( MealCompositions.quantity * ingredientsRatio/ 100) AS alcohol\n" + "FROM Meals LEFT JOIN MealCompositions ON Meals.mealName = MealCompositions.mealName\n" + "LEFT JOIN Ingredients ON MealCompositions.ingredientName = Ingredients.ingredientName\n" + "JOIN EatenMeals ON MealCompositions.mealName = EatenMeals.mealName\n" + "WHERE username = '%s'\n" + "ORDER BY mealNumber;", username));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectFromDailyProgressesTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT burnedCalories, date \n" + "   FROM DailyProgresses \n" + "   WHERE username = '%s' ", username));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectFromMealPlanTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT beginningDay \n" + "       FROM MealPlan \n" + "       WHERE username = '%s'", username));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectDailyMeals(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            String query = String.format("SELECT dayNumber,\n" +
                    "       mealNumber,\n" + "       DailyMeals.mealName AS mealName,\n" + "       Ingredients.ingredientName AS ingredientName,\n" + "       MealCompositions.quantity AS quantity,\n" + "       quantityType,\n" + "       ingredientsRatio,\n" + "       gramsPerQuantity,\n" + "       price * ( ingredientsRatio * MealCompositions.quantity  / 100) AS price,\n" + "       complexcarbs * ( ingredientsRatio * MealCompositions.quantity  / 100 ) AS complexCarbs,\n" + "       simplecarbs * ( ingredientsRatio * MealCompositions.quantity  / 100) AS simpleCarbs,\n" + "       fibers * ( ingredientsRatio * MealCompositions.quantity  / 100) AS fibers,\n" + "       saturatedfats * ( ingredientsRatio * MealCompositions.quantity  / 100 ) AS saturatedFats,\n" + "       unsaturatedfats * ( ingredientsRatio * MealCompositions.quantity  / 100 ) AS unSaturatedFats,\n" + "       transfats * ( ingredientsRatio * MealCompositions.quantity  / 100) AS transFats,\n" + "       proteins * ( ingredientsRatio * MealCompositions.quantity  / 100) as proteins,\n" + "       alcohol * ( ingredientsRatio * MealCompositions.quantity  / 100) AS alcohol\n" + "FROM\n" + "    DailyMeals JOIN Meals ON DailyMeals.mealName = Meals.mealName" + "    LEFT JOIN MealCompositions ON DailyMeals.mealName = MealCompositions.mealName\n" + "    LEFT JOIN Ingredients on MealCompositions.ingredientName = Ingredients.ingredientName\n" + "WHERE username = '%s'\n" + "ORDER BY DailyMeals.dayNumber, DailyMeals.mealNumber", username);
            ResultSet resultSet = statement.executeQuery(query);
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectFromGroceriesItemTable() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT " + "   status," + "   Ingredients.ingredientName AS ingredientName," + "   GroceriesItems.quantity AS quantity," + "   quantityType," + "   gramsPerQuantity," + "  \n" +
                    "   price * (GroceriesItems.quantity  / 100) AS price,\n" + "   complexcarbs * ( GroceriesItems.quantity  / 100 ) AS complexCarbs,\n" + "   simplecarbs * ( GroceriesItems.quantity  / 100) AS simpleCarbs,\n" + "   fibers * ( GroceriesItems.quantity  / 100) AS fibers,\n" + "   saturatedfats * ( GroceriesItems.quantity  / 100 ) AS saturatedFats,\n" + "   unSaturatedfats * ( GroceriesItems.quantity  / 100 ) AS unSaturatedFats,\n" + "   transfats * ( GroceriesItems.quantity  / 100) AS transFats,\n" + "   proteins * ( GroceriesItems.quantity  / 100) as proteins,\n" + "   alcohol * ( GroceriesItems.quantity  / 100) AS alcohol\n" + "FROM \n" + "GroceriesItems JOIN Ingredients ON GroceriesItems.ingredientName = Ingredients.ingredientName\n");
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectEveryIngredient() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM Ingredients");
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectEveryMeal() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT Meals.mealName AS mealName,\n" + "       Ingredients.ingredientName AS ingredientName,\n" + "       quantityType,\n" + "       MealCompositions.quantity AS quantity,\n" + "       gramsPerQuantity,\n" + "       price * (  MealCompositions.quantity / 100) AS price,\n" + "       complexCarbs * ( MealCompositions.quantity / 100) AS complexCarbs,\n" + "       simpleCarbs * ( MealCompositions.quantity / 100) AS simpleCarbs,\n" + "       fibers * ( MealCompositions.quantity / 100) AS fibers,\n" + "       saturatedFats * ( MealCompositions.quantity / 100) AS saturatedFats,\n" + "       unSaturatedFats * (  MealCompositions.quantity / 100) AS unSaturatedFats,\n" + "       transFats * (  MealCompositions.quantity / 100) AS transFats,\n" + "       proteins * (  MealCompositions.quantity / 100) AS proteins,\n" + "       alcohol * (  MealCompositions.quantity / 100) AS alcohol\n" + "       FROM Meals LEFT JOIN MealCompositions ON Meals.mealName = MealCompositions.mealName" + "       LEFT JOIN Ingredients ON MealCompositions.ingredientName = Ingredients.ingredientName\n");
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectIngredientsThatStartWith(String ingredientName) throws SQLException {
        Connection connection = DBManager.getConnection();
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            String query = String.format("SELECT * FROM Ingredients WHERE ingredientName LIKE '%s%c'", ingredientName, '%');
            ResultSet resultSet = statement.executeQuery(query);
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectMealsThatStartWith(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            String query = String.format("\n" + "SELECT Meals.mealName AS mealName,\n" + "       Ingredients.ingredientName AS ingredientName,\n" + "       quantityType,\n" + "       gramsPerQuantity,\n" + "       MealCompositions.quantity AS quantity,\n" + "       price * ( MealCompositions.quantity / 100) AS price,\n" + "       complexCarbs * ( MealCompositions.quantity / 100) AS complexCarbs,\n" + "       simpleCarbs * (  MealCompositions.quantity / 100) AS simpleCarbs,\n" + "       fibers * (  MealCompositions.quantity / 100) AS fibers,\n" + "       saturatedFats * ( MealCompositions.quantity / 100) AS saturatedFats,\n" + "       unSaturatedFats * (  MealCompositions.quantity / 100) AS unSaturatedFats,\n" + "       transFats * (  MealCompositions.quantity / 100) AS transFats,\n" + "       proteins * (  MealCompositions.quantity / 100) AS proteins,\n" + "       alcohol * ( MealCompositions.quantity / 100) AS alcohol\n" + "    FROM Meals LEFT JOIN MealCompositions ON Meals.mealName = MealCompositions.mealName\n" + "    LEFT JOIN Ingredients ON MealCompositions.ingredientName = Ingredients.ingredientName\n" + "    WHERE Meals.mealName LIKE '%s%c'", mealName, '%', '%');
            ResultSet resultSet = statement.executeQuery(query);
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet findMealInTables(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            String query = String.format("" + "SELECT mealName\n" + "FROM DailyMeals\n" + "WHERE MealName = '%s'\n" + "UNION\n" + "SELECT mealName\n" + "FROM EatenMeals\n" + "WHERE MealName = '%s" + "'", mealName, mealName);
            ResultSet resultSet = statement.executeQuery(query);
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet findIngredientInTables(String ingredientName) throws SQLException {
        Connection connection = DBManager.getConnection();
        CachedRowSet cachedRowSet;
        try (Statement statement = connection.createStatement()) {
            String query = String.format("SELECT ingredientName\n" + "FROM main.GroceriesItems\n" + "WHERE ingredientName='%s'\n" + "UNION\n" + "SELECT ingredientName\n" + "FROM main.MealCompositions\n" + "WHERE ingredientName='%s'", ingredientName, ingredientName);
            ResultSet resultSet = statement.executeQuery(query);
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }
}
