package pickyeater.database.SQLutils;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;
import pickyeater.basics.user.UserGoal;
import pickyeater.basics.user.UserStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class SQLUnSafeQueryExecutor {

    public void insertIntoIngredientsTable(Ingredient ingredient) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        String query = "INSERT INTO Ingredients " +
                "(ingredientName,price,quantity,quantityType,gramsPerQuantity,complexCarbs,simpleCarbs," +
                "fibers,saturatedFats,unsaturatedFats,transFats,proteins,alcohol)\n" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) " +
                "ON CONFLICT (ingredientName) DO UPDATE SET " +
                "price = ?," +
                "quantityType = ?," +
                "gramsPerQuantity = ?," +
                "complexCarbs = ?," +
                "simpleCarbs = ?," +
                "fibers = ?," +
                "saturatedFats = ?," +
                "unsaturatedFats = ?," +
                "transFats = ?," +
                "proteins = ?," +
                "alcohol = ?\n" +
                "WHERE ingredientName = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            Nutrients nutrients = ingredient.getNutrients();
            // Insert values
            statement.setString(1,ingredient.getName());
            statement.setFloat(2,ingredient.getPrice());
            statement.setFloat(3,ingredient.getQuantity().getAmount());
            statement.setString(4,ingredient.getQuantity().getQuantityType().name());
            statement.setFloat(5,ingredient.getQuantity().getGramsPerQuantity());
            statement.setFloat(6,nutrients.getComplexCarbs());
            statement.setFloat(7,nutrients.getSimpleCarbs());
            statement.setFloat(8,nutrients.getFibers());
            statement.setFloat(9,nutrients.getSaturatedFats());
            statement.setFloat(10,nutrients.getUnSaturatedFats());
            statement.setFloat(11,nutrients.getTransFats());
            statement.setFloat(12,nutrients.getProteins());
            statement.setFloat(13,nutrients.getAlcohol());

            // Update values
            statement.setFloat(14,ingredient.getPrice());
            statement.setString(15,ingredient.getQuantity().getQuantityType().name());
            statement.setFloat(16,ingredient.getQuantity().getGramsPerQuantity());
            statement.setFloat(17,nutrients.getComplexCarbs());
            statement.setFloat(18,nutrients.getSimpleCarbs());
            statement.setFloat(19,nutrients.getFibers());
            statement.setFloat(20,nutrients.getSaturatedFats());
            statement.setFloat(21,nutrients.getUnSaturatedFats());
            statement.setFloat(22,nutrients.getTransFats());
            statement.setFloat(23,nutrients.getProteins());
            statement.setFloat(24,nutrients.getAlcohol());
            statement.setString(25,ingredient.getName());
            statement.execute();
        }
        connection.commit();
    }

    public void insertIntoMealsTable(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        try(Statement statement = connection.createStatement()) {
            statement.execute(
                    String.format("INSERT INTO Meals (mealName) VALUES ('%s')" +
                            "ON CONFLICT (mealName) DO NOTHING ",mealName));
        }
        connection.commit();
    }

    public void insertIntoMealCompositionsTable(Meal meal) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);

        try(Statement statement = connection.createStatement()){
            String query = String.format("DELETE FROM MealCompositions WHERE mealName = '%s'",meal.getName());
            statement.execute(query);
        }
        if(meal.getIngredients().isEmpty()){
            connection.commit();
            return;
        }

        // Insert new meals or edits the quantity of already existing ones
        for (Ingredient ingredient : meal.getIngredients()) {
            try(Statement statement = connection.createStatement()) {
                String mealCompositionQuery = String.format("INSERT INTO MealCompositions (ingredientName, mealName, quantity)" +
                                " VALUES ('%s','%s','%s') ON CONFLICT (ingredientName,mealName) DO " +
                                " UPDATE SET quantity = %s ",
                        ingredient.getName(),
                        meal.getName(),
                        ingredient.getQuantity().getGramsPerQuantity(),
                        String.format("%f", ingredient.getQuantity().getGramsPerQuantity()).replaceAll(",","."));
                statement.execute(mealCompositionQuery);
            }
        }
        connection.commit();
    }

    public void inertIntoUserTable(User user) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        // Deletes record if exists
        connection.createStatement().execute(
                String.format("DELETE FROM User WHERE username = '%s'",user.getName()));
        // Insert new record
        String query = "INSERT INTO User " +
                "(username, sex, bodyFat, height, weight, dateOfBirth, " +
                "weightGoal, lifeStyle, neededCarbs, neededFats, neededProteins) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,user.getName());
            UserStatus userStatus = user.getUserStatus();
            statement.setString(2,userStatus.getSex().name());
            statement.setFloat(3,userStatus.getBodyFat());
            statement.setInt(4,userStatus.getHeight());
            statement.setFloat(5,userStatus.getWeight());
            statement.setLong(6,userStatus.getDateOfBirth().toEpochDay());
            UserGoal userGoal = user.getUserGoal();
            statement.setString(7,userGoal.getWeightVariationGoal().name());
            statement.setString(8,userGoal.getLifeStyle().name());
            Nutrients requiredNutrients = userGoal.getRequiredNutrients();
            statement.setFloat(9,requiredNutrients.getCarbs());
            statement.setFloat(10,requiredNutrients.getFats());
            statement.setFloat(11,requiredNutrients.getProteins());
            statement.execute();
        }
        connection.commit();
    }

    public void insertIntoMealPlanTable(String userName, LocalDate beginningDay) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        String query = "INSERT INTO MealPlan (username, beginningDay) " +
                "VALUES (?, ?) ON CONFLICT(username) DO\n" +
                "UPDATE SET beginningDay = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,userName);
            statement.setLong(2,beginningDay.toEpochDay());
            statement.setLong(3,beginningDay.toEpochDay());
            statement.execute();
        }
        connection.commit();
    }

    public void insertIntoDailyMealsTable(String userName, List<DailyMealPlan> dailyMealPlans) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);

        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM DailyMeals WHERE " +
                            "username = '%s'",userName));
        }

        if(dailyMealPlans.isEmpty()){
            connection.commit();
            return;
        }


        for (int dayNumber = 0; dayNumber < dailyMealPlans.size(); dayNumber++) {
            DailyMealPlan dailyMealPlan = dailyMealPlans.get(dayNumber);
            List<Meal> meals = dailyMealPlan.getMeals();
            for (int mealNumber = 0; mealNumber < meals.size(); mealNumber++) {
                Meal meal = meals.get(mealNumber);
                try(Statement statement = connection.createStatement()){
                    float ingredientsRatio = meal.getWeight()/100;
                    String query = String.format("INSERT INTO DailyMeals (mealName, username, dayNumber, mealNumber,ingredientsRatio)" +
                                    " VALUES ('%s','%s',%d,%d,%s)",
                            meal.getName(),
                            userName,
                            dayNumber,
                            mealNumber,
                            String.format("%f",ingredientsRatio).replaceAll(",",".")
                            );
                    statement.execute(query);
                }

            }
        }
        connection.commit();
    }

    public void insertIntoDailyProgressesTable(String username, DailyProgresses dailyProgresses) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);

        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "INSERT INTO DailyProgresses (username, burnedCalories, date) VALUES ('%s', %d, %d) " +
                            "ON CONFLICT DO UPDATE SET " +
                            "burnedCalories = %d," +
                            "date = %d ",
                    username,dailyProgresses.getBurnedCalories(),dailyProgresses.getDate().toEpochDay(),
                    dailyProgresses.getBurnedCalories(),dailyProgresses.getDate().toEpochDay()));
        }

        connection.commit();
    }

    public void insertIntoEatenMealsTable(String userName, List<Meal> eatenMeals) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        try(Statement statement = connection.createStatement()){
            String query = String.format("DELETE FROM EatenMeals WHERE userName = '%s'",userName);
            statement.execute(query);
        }
        if(eatenMeals.isEmpty()){
            connection.commit();
            return;
        }

        for (int i = 0; i < eatenMeals.size(); i++) {
            Meal eatenMeal = eatenMeals.get(i);

            try(Statement statement = connection.createStatement()){
                float ingredientsRatio = eatenMeal.getWeight() / 100;
                statement.execute(
                        String.format("INSERT INTO EatenMeals (username, mealName, mealNumber, ingredientsRatio)" +
                        " VALUES ('%s','%s',%d,%s)",
                                userName,
                                eatenMeal.getName(),
                                i,
                                String.format("%f",ingredientsRatio).replaceAll(",",".")
                        ));
            }

        }
        connection.commit();
    }

    public void insertIntoGroceriesTable(GroceriesCheckList checkList) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);

        try(Statement statement = connection.createStatement()){
            String query = "DELETE FROM GroceriesItems";
            statement.execute(query);
        }

        insertGroceriesIngredient(checkList.getNeededIngredients(), connection, "NEEDED");
        insertGroceriesIngredient(checkList.getMissingIngredients(), connection, "MISSING");
        insertGroceriesIngredient(checkList.getTakenIngredients(), connection, "TAKEN");
        connection.commit();
    }

    private void insertGroceriesIngredient(Set<Ingredient> checkList, Connection connection, String status) throws SQLException {

        for (Ingredient neededIngredient : checkList) {

            try(Statement statement = connection.createStatement()){
                statement.execute(String.format("INSERT INTO GroceriesItems " +
                                "(ingredientName, quantity, status) VALUES ('%s',%s,'%s')",
                        neededIngredient.getName(),
                        String.format("%f",neededIngredient.getQuantity().getAmount()).replaceAll(",","."),
                        status));
            }
        }
    }

    public void deleteFromIngredientsTable(String ingredientName) throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM Ingredients WHERE ingredientName = '%s'",ingredientName));
        }
        connection.commit();
    }

    public void deleteFromMealsTable(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM Meals WHERE mealName = '%s'",mealName));
        }
        connection.commit();
    }

    public void deleteFromMealCompositionsTable(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM MealCompositions WHERE mealName = '%s'",mealName));
        }
        connection.commit();
    }

    public void deleteFromUserTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM User WHERE username = '%s'",username));
        }
        connection.commit();
    }

    public void deleteFromMealPlanTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM MealPlan WHERE username = '%s'",username));
        }
        connection.commit();
    }

    public void deleteFromDailyMealsTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM DailyMeals WHERE username = '%s'",username));
        }
        connection.commit();
    }

    public void deleteFromDailyProgressesTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM DailyProgresses WHERE username = '%s'",username));
        }
        connection.commit();
    }

    public void deleteFromEatenMealsTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute(String.format(
                    "DELETE FROM EatenMeals WHERE username = '%s'",username));
        }
    }

    public void deleteFromGroceriesTable() throws SQLException {
        Connection connection = DBManager.getConnection();
        try(Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM GroceriesItems");
        }
        connection.commit();
    }
}
