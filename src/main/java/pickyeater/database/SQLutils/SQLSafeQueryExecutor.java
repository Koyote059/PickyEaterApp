package pickyeater.database.SQLutils;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSafeQueryExecutor {

    public ResultSet selectFromUserTable() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery("SELECT * FROM User");
            cachedRowSet = cache(resultSet);
        }

        return cachedRowSet;
    }

    public ResultSet selectIngredient(String ingredientName) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(
                    String.format("SELECT * FROM Ingredients WHERE ingredientName = '%s'",ingredientName));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectMeal(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(
                    String.format("SELECT mealName,\n" +
                            "       Ingredients.ingredientName AS ingredientName,\n" +
                            "       quantityType,\n" +
                            "       quantity,\n" +
                            "       price * ( gramsPerQuantity * MealCompositions.quantity / 100) AS price,\n" +
                            "       complexCarbs * ( gramsPerQuantity * MealCompositions.quantity / 100) AS complexCarbs,\n" +
                            "       simpleCarbs * ( gramsPerQuantity * MealCompositions.quantity / 100) AS simpleCarbs,\n" +
                            "       fibers * ( gramsPerQuantity * MealCompositions.quantity / 100) AS fibers,\n" +
                            "       saturatedFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS saturatedFats,\n" +
                            "       unSaturatedFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS unSaturatedFats,\n" +
                            "       transFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS transFats,\n" +
                            "       proteins * ( gramsPerQuantity * MealCompositions.quantity / 100) AS proteins,\n" +
                            "       alcohol * ( gramsPerQuantity * MealCompositions.quantity / 100) AS alcohol\n" +
                            "FROM MealCompositions JOIN Ingredients ON MealCompositions.ingredientName = Ingredients.ingredientName\n" +
                            "WHERE mealName = '%s'",mealName));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectFromEatenMealsTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(
                    String.format("SELECT MealCompositions.mealName AS mealName,\n" +
                            "       Ingredients.ingredientName AS ingredientName,\n" +
                            "       quantity,\n" +
                            "       quantityType,\n" +
                            "       quantityType,\n" +
                            "       ingredientsRatio,\n" +
                            "       gramsPerQuantity,\n" +
                            "       price * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS price,\n" +
                            "       complexCarbs * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS complexCarbs,\n" +
                            "       simpleCarbs * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS simpleCarbs,\n" +
                            "       fibers * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS fibers,\n" +
                            "       saturatedFats * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS saturatedFats,\n" +
                            "       unSaturatedFats * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS unSaturatedFats,\n" +
                            "       transFats * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS transFats,\n" +
                            "       proteins * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS proteins,\n" +
                            "       alcohol * ( ingredientsRatio * gramsPerQuantity * MealCompositions.quantity / 100) AS alcohol\n" +
                            "FROM MealCompositions JOIN Ingredients ON MealCompositions.ingredientName = Ingredients.ingredientName\n" +
                            "JOIN EatenMeals ON MealCompositions.mealName = EatenMeals.mealName\n" +
                            "WHERE username = '%s'\n" +
                            "ORDER BY mealNumber;",username));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectFromDailyProgressesTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(
                    String.format("SELECT burnedCalories, date \n" +
                            "   FROM DailyProgresses \n" +
                            "   WHERE username = '%s' ",username));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectFromMealPlanTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(
                    String.format("SELECT beginningDay \n" +
                            "       FROM MealPlan \n" +
                            "       WHERE username = '%s'",username));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectDailyMeals(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT dayNumber,\n" +
                            "       DailyMeals.mealName AS mealName,\n" +
                            "       Ingredients.ingredientName AS ingredientName,\n" +
                            "       quantity,\n" +
                            "       quantityType,\n" +
                            "       ingredientsRatio, \n" +
                            "       gramsPerQuantity,\n" +
                            "       price * ( ingredientsRatio * quantity * gramsPerQuantity / 100) AS price,\n" +
                            "       complexcarbs * ( ingredientsRatio * quantity * gramsPerQuantity / 100 ) AS complexCarbs,\n" +
                            "       simplecarbs * ( ingredientsRatio * quantity * gramsPerQuantity / 100) AS simpleCarbs,\n" +
                            "       fibers * ( ingredientsRatio * quantity * gramsPerQuantity / 100) AS fibers,\n" +
                            "       saturatedfats * ( ingredientsRatio * quantity * gramsPerQuantity / 100 ) AS saturatedFats,\n" +
                            "       unsaturatedfats * ( ingredientsRatio * quantity * gramsPerQuantity / 100 ) AS unSaturatedFats,\n" +
                            "       transfats * ( ingredientsRatio * quantity * gramsPerQuantity / 100) AS transFats,\n" +
                            "       proteins * ( ingredientsRatio * quantity * gramsPerQuantity / 100) as proteins,\n" +
                            "       alcohol * ( ingredientsRatio * quantity * gramsPerQuantity / 100) AS alcohol\n" +
                            "FROM\n" +
                            "    DailyMeals JOIN MealCompositions ON DailyMeals.mealName = MealCompositions.mealName\n" +
                            "    JOIN Ingredients on MealCompositions.ingredientName = Ingredients.ingredientName\n" +
                            "    WHERE username = '%s'\n" +
                            "    ORDER BY DailyMeals.dayNumber, DailyMeals.mealNumber",username));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectFromGroceriesItemTable() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery("SELECT " +
                            "   status," +
                            "   Ingredients.ingredientName AS ingredientName," +
                            "   quantity," +
                            "   quantityType,"+
                            "   gramsPerQuantity,"+
                            "   price * (quantity * gramsPerQuantity / 100) AS price,\n" +
                            "   complexcarbs * ( quantity * gramsPerQuantity / 100 ) AS complexCarbs,\n" +
                            "   simplecarbs * ( quantity * gramsPerQuantity / 100) AS simpleCarbs,\n" +
                            "   fibers * ( quantity * gramsPerQuantity / 100) AS fibers,\n" +
                            "   saturatedfats * ( quantity * gramsPerQuantity / 100 ) AS saturatedFats,\n" +
                            "   unSaturatedfats * ( quantity * gramsPerQuantity / 100 ) AS unSaturatedFats,\n" +
                            "   transfats * ( quantity * gramsPerQuantity / 100) AS transFats,\n" +
                            "   proteins * ( quantity * gramsPerQuantity / 100) as proteins,\n" +
                            "   alcohol * ( quantity * gramsPerQuantity / 100) AS alcohol\n" +
                            "FROM \n" +
                            "GroceriesItems JOIN Ingredients ON GroceriesItems.ingredientName = Ingredients.ingredientName\n");
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectEveryIngredient() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery("SELECT * FROM Ingredients");
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectEveryMeal() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery("SELECT mealName,\n" +
                            "       Ingredients.ingredientName AS ingredientName,\n" +
                            "       quantityType,\n" +
                            "       quantity,\n" +
                            "       price * ( gramsPerQuantity * MealCompositions.quantity / 100) AS price,\n" +
                            "       complexCarbs * ( gramsPerQuantity * MealCompositions.quantity / 100) AS complexCarbs,\n" +
                            "       simpleCarbs * ( gramsPerQuantity * MealCompositions.quantity / 100) AS simpleCarbs,\n" +
                            "       fibers * ( gramsPerQuantity * MealCompositions.quantity / 100) AS fibers,\n" +
                            "       saturatedFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS saturatedFats,\n" +
                            "       unSaturatedFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS unSaturatedFats,\n" +
                            "       transFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS transFats,\n" +
                            "       proteins * ( gramsPerQuantity * MealCompositions.quantity / 100) AS proteins,\n" +
                            "       alcohol * ( gramsPerQuantity * MealCompositions.quantity / 100) AS alcohol\n" +
                            "FROM MealCompositions JOIN Ingredients ON MealCompositions.ingredientName = Ingredients.ingredientName\n");
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectIngredientsThatStartWith(String ingredientName) throws SQLException {
        Connection connection = DBManager.getConnection();
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT * FROM Ingredients WHERE ingredientName LIKE '%s'",ingredientName));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }

    public ResultSet selectMealsThatStartWith(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        CachedRowSet cachedRowSet;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT mealName,\n" +
                            "       Ingredients.ingredientName AS ingredientName,\n" +
                            "       quantityType,\n" +
                            "       quantity,\n" +
                            "       price * ( gramsPerQuantity * MealCompositions.quantity / 100) AS price,\n" +
                            "       complexCarbs * ( gramsPerQuantity * MealCompositions.quantity / 100) AS complexCarbs,\n" +
                            "       simpleCarbs * ( gramsPerQuantity * MealCompositions.quantity / 100) AS simpleCarbs,\n" +
                            "       fibers * ( gramsPerQuantity * MealCompositions.quantity / 100) AS fibers,\n" +
                            "       saturatedFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS saturatedFats,\n" +
                            "       unSaturatedFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS unSaturatedFats,\n" +
                            "       transFats * ( gramsPerQuantity * MealCompositions.quantity / 100) AS transFats,\n" +
                            "       proteins * ( gramsPerQuantity * MealCompositions.quantity / 100) AS proteins,\n" +
                            "       alcohol * ( gramsPerQuantity * MealCompositions.quantity / 100) AS alcohol\n" +
                            "FROM MealCompositions JOIN Ingredients ON MealCompositions.ingredientName = Ingredients.ingredientName\n" +
                            "WHERE mealName LIKE '%s'",mealName));
            cachedRowSet = cache(resultSet);
        }
        return cachedRowSet;
    }
    private CachedRowSet cache(ResultSet resultSet) throws SQLException {
        CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
        if(!resultSet.isBeforeFirst()){
            resultSet.close();
            return cachedRowSet;
        }
        cachedRowSet.populate(resultSet);
        return cachedRowSet;
    }
}
