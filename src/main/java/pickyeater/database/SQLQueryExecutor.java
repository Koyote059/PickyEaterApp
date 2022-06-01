package pickyeater.database;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.user.User;
import pickyeater.basics.user.UserGoal;
import pickyeater.basics.user.UserStatus;
import pickyeater.database.SQLutils.DBManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class SQLQueryExecutor {
    public SQLQueryExecutor(String dbPath) {
        DBManager.setConnection(DBManager.JDBC_Driver_SQLite, String.format("jdbc:sqlite:%s", dbPath));
    }

    public void createTables() throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS Ingredients (\n" + "ingredientName VARCHAR(32),\n" + "price SMALLMONEY,\n" + "quantityType VARCHAR(16),\n" + "gramsPerQuantity REAL," + "complexCarbs REAL,\n" + "simpleCarbs REAL,\n" + "fibers REAL,\n" + "saturatedFats REAL,\n" + "unsaturatedFats REAL,\n" + "transFats REAL,\n" + "proteins REAL,\n" + "alcohol REAL,\n" + "CONSTRAINT ILLEGAL_ARGUMENT_QUANTITYTYPE CHECK (quantityType = 'GRAMS' OR quantityType = 'MILLILITERS' OR quantityType = 'PIECES'),\n" + "CONSTRAINT PK_INGREDIENTS PRIMARY KEY (ingredientName)\n" + ");");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS Meals (\n" + "    mealName VARCHAR(32),\n" + "    CONSTRAINT PK_MEALS PRIMARY KEY (mealName)\n" + ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS MealCompositions (\n" + "    ingredientName VARCHAR(32),\n" + "    mealName VARCHAR(32),\n" + "    quantity REAL,\n" + "    CONSTRAINT PK_MEALCOMPOSITIONS PRIMARY KEY (ingredientName,mealName),\n" + "    CONSTRAINT FK_MEALCOMPOSITIONS_INGREDIENTNAME FOREIGN KEY (ingredientName) REFERENCES Ingredients,\n" + "    CONSTRAINT FK_MEALCOMPOSITIONS_MEALNAME FOREIGN KEY (mealName) REFERENCES Meals\n" + ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS User (\n" + "    username VARCHAR(32),\n" + "    sex VARCHAR(8),\n" + "    bodyFat REAL,\n" + "    height SMALLINT,\n" + "    weight REAL,\n" + "    dateOfBirth SMALLDATE,\n" + "    weightGoal VARCHAR(16),\n" + "    lifeStyle VARCHAR(16),\n" + "    neededCarbs REAL,\n" + "    neededFats REAL,\n" + "    neededProteins REAL,\n" + "    CONSTRAINT PK_USER PRIMARY KEY (username),\n" + "    CONSTRAINT ILLEGAL_ARGUMENT_SEX CHECK ( sex = 'MALE' OR sex = 'FEMALE'),\n" + "    CONSTRAINT ILLEGAL_ARGUMENT_WEIGHTGOAL CHECK\n" + "        ( weightGoal = 'LOSE_WEIGHT' OR weightGoal = 'MANTAIN_WEIGHT' OR weightGoal = 'INCREASE_WEIGHT'  ),\n" + "    CONSTRAINT ILLEGAL_ARGUMENT_LIFESTYLE CHECK\n" + "        ( lifeStyle = 'SEDENTARY' OR lifeStyle = 'LIGHTLY_ACTIVE' OR lifeStyle = 'ACTIVE' OR lifeStyle = 'VERY_ACTIVE')\n" + ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS MealPlan(\n" + "    username VARCHAR(32),\n" + "    beginningDay SMALLDATE,\n" + "    CONSTRAINT PK_MEALPLAN PRIMARY KEY (username),\n" + "    CONSTRAINT FK_MEALPLAN_USERNAME FOREIGN KEY (username) REFERENCES User\n" + ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS DailyMeals (\n" + "    mealName VARCHAR(32),\n" + "    username VARCHAR(32),\n" + "    dayNumber TINYINT,\n" + "    mealNumber TINYINT,\n" + "    weight REAL,\n" + "    CONSTRAINT PK_DAILYMEALS PRIMARY KEY (mealName,username,dayNumber,mealNumber),\n" + "    CONSTRAINT FK_DAILYMEALS_MEALNAME FOREIGN KEY (mealName) REFERENCES Meals,\n" + "    CONSTRAINT FK_DAILYMEALS_USERNAME FOREIGN KEY (username) REFERENCES MealPlan\n" + ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS DailyProgresses (\n" + "    username VARCHAR(32),\n" + "    burnedCalories SMALLINT,\n" + "    CONSTRAINT PK_DAILYPROGRESSES PRIMARY KEY (username),\n" + "    CONSTRAINT FK_DAILYPROGRESSES_USERNAME FOREIGN KEY (username) REFERENCES User\n" + ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS EatenMeals (\n" + "    username VARCHAR(32),\n" + "    mealName VARCHAR(32),\n" + "    mealNumber TINYINT,\n" + "    weight REAL,\n" + "    CONSTRAINT PK_DAILYPROGRESSES PRIMARY KEY (username,mealName,mealNumber),\n" + "    CONSTRAINT FK_DAILYPROGRESSES_USERNAME FOREIGN KEY (username) REFERENCES DailyProgresses\n" + ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS GroceriesItems (\n" + "    username VARCHAR(32),\n" + "    ingredientName VARCHAR(32),\n" + "    quantity REAL,\n" + "    status VARCHAR(16),\n" + "    CONSTRAINT PK_DAILYPROGRESSES PRIMARY KEY (username,ingredientName),\n" + "    CONSTRAINT FK_DAILYPROGRESSES_USERNAME FOREIGN KEY (username) REFERENCES User,\n" + "    CONSTRAINT FK_DAILYPROGRESSES_INGREDIENTNAME FOREIGN KEY (ingredientName) REFERENCES Ingredients,\n" + "    CONSTRAINT ILLEGAL_ARGUMENT_STATUS CHECK (status = 'MISSING' OR status = 'NEEDED' OR status = 'TAKEN')" + ")");
        connection.commit();
    }

    public void insertIntoIngredientsTable(Ingredient ingredient) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        String query = "INSERT INTO Ingredients " + "(ingredientName,price,quantityType,gramsPerQuantity,complexCarbs,simpleCarbs," + "fibers,saturatedFats,unsaturatedFats,transFats,proteins,alcohol)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?) " + "ON CONFLICT (ingredientName) DO (UPDATE Ingredients SET " + "price = ?," + "quantityType = ?," + "gramsPerQuantity = ?," + "complexCarbs = ?," + "simpleCarbs = ?," + "fibers = ?," + "saturatedFats = ?," + "unsaturatedFats = ?," + "transFats = ?," + "proteins = ?," + "alcohol = ?," + "WHERE ingredientName = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        Nutrients nutrients = ingredient.getNutrients();
        // Insert values
        statement.setString(1, ingredient.getName());
        statement.setFloat(2, ingredient.getPrice());
        statement.setString(3, ingredient.getQuantity().getQuantityType().name());
        statement.setFloat(4, nutrients.getComplexCarbs());
        statement.setFloat(5, nutrients.getSimpleCarbs());
        statement.setFloat(6, nutrients.getFibers());
        statement.setFloat(7, nutrients.getSaturatedFats());
        statement.setFloat(8, nutrients.getUnSaturatedFats());
        statement.setFloat(9, nutrients.getTransFats());
        statement.setFloat(10, nutrients.getProteins());
        statement.setFloat(11, nutrients.getAlcohol());
        // Update values
        statement.setFloat(12, ingredient.getPrice());
        statement.setString(13, ingredient.getQuantity().getQuantityType().name());
        statement.setFloat(14, nutrients.getComplexCarbs());
        statement.setFloat(15, nutrients.getSimpleCarbs());
        statement.setFloat(16, nutrients.getFibers());
        statement.setFloat(17, nutrients.getSaturatedFats());
        statement.setFloat(18, nutrients.getUnSaturatedFats());
        statement.setFloat(19, nutrients.getTransFats());
        statement.setFloat(20, nutrients.getProteins());
        statement.setFloat(21, nutrients.getAlcohol());
        statement.setString(22, ingredient.getName());
        statement.execute();
        connection.commit();
    }

    public void insertIntoMealsTable(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        connection.createStatement().execute(String.format("INSERT INTO Meals (mealName) VALUES ('%s')" + "ON CONFLICT (mealName) DO NOTHING ", mealName));
        connection.commit();
    }

    public void insertIntoMealCompositionsTable(Meal meal) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        if (meal.getIngredients().isEmpty())
            return;
        // Deletes every ingredient that is not actual in the meal
        StringBuilder queryBuilder = new StringBuilder(String.format("DELETE FROM MealCompositions WHERE " + "mealName = %s AND ingredientName NOT IN (", meal.getName()));
        Set<Ingredient> ingredients = meal.getIngredients();
        int i = 0;
        for (Ingredient ingredient : ingredients) {
            queryBuilder.append(ingredient.getName());
            i++;
            if (i < ingredients.size())
                queryBuilder.append(", ");
        }
        queryBuilder.append(" )");
        connection.createStatement().execute(queryBuilder.toString());
        // Insert new meals or edits the quantity of already existing ones
        for (Ingredient ingredient : meal.getIngredients()) {
            connection.createStatement().execute(String.format("INSERT INTO MealCompositions (ingredientName, mealName, quantity)" + " VALUES ('%s','%s','%s') ON CONFLICT (ingredientName,mealName) DO " + " UPDATE SET quantity = %f ", ingredient.getName(), meal.getName(), ingredient.getQuantity().getGramsPerQuantity(), ingredient.getQuantity().getGramsPerQuantity()));
        }
        connection.commit();
    }

    public void inertIntoUserTable(User user) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        // Deletes record if exists
        connection.createStatement().execute(String.format("DELETE FROM User WHERE username = %s", user.getName()));
        // Insert new record
        String query = "INSERT INTO User " + "(username, sex, bodyFat, height, weight, dateOfBirth, " + "weightGoal, lifeStyle, neededCarbs, neededFats, neededProteins) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getName());
        UserStatus userStatus = user.getUserStatus();
        statement.setString(2, userStatus.getSex().name());
        statement.setFloat(3, userStatus.getBodyFat());
        statement.setInt(4, userStatus.getHeight());
        statement.setFloat(5, userStatus.getWeight());
        Date dateOfBirth = new Date(userStatus.getDateOfBirth().toEpochDay());
        statement.setDate(6, dateOfBirth);
        UserGoal userGoal = user.getUserGoal();
        statement.setString(7, userGoal.getWeightVariationGoal().name());
        statement.setString(8, userGoal.getLifeStyle().name());
        Nutrients requiredNutrients = userGoal.getRequiredNutrients();
        statement.setFloat(9, requiredNutrients.getCarbs());
        statement.setFloat(10, requiredNutrients.getFats());
        statement.setFloat(11, requiredNutrients.getProteins());
        statement.execute();
        connection.commit();
    }

    public void insertIntoMealPlanTable(String userName, LocalDate beginningDay) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        String query = "INSERT INTO MealPlan (username, beginningDay) " + "VALUES (?, ?) ON CONFLICT(username) DO" + "UPDATE SET beginningDay = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, userName);
        Date date = new Date(beginningDay.toEpochDay());
        statement.setDate(2, date);
        statement.setDate(3, date);
        statement.execute();
        connection.commit();
    }

    public void insertIntoDailyMealsTable(String userName, List<DailyMealPlan> dailyMealPlans) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        if (dailyMealPlans.isEmpty())
            return;
        connection.createStatement().execute(String.format("DELETE FROM DailyMeals WHERE " + "username = %s", userName));
        for (int dayNumber = 0; dayNumber < dailyMealPlans.size(); dayNumber++) {
            DailyMealPlan dailyMealPlan = dailyMealPlans.get(dayNumber);
            List<Meal> meals = dailyMealPlan.getMeals();
            for (int mealNumber = 0; mealNumber < meals.size(); mealNumber++) {
                Meal meal = meals.get(mealNumber);
                connection.createStatement().execute(String.format("INSERT INTO DailyMeals (mealName, username, dayNumber, mealNumber, weight)" + " VALUES ('%s','%s',%d,%d, %f)", meal.getName(), userName, dayNumber, mealNumber, meal.getWeight()));
            }
        }
        connection.commit();
    }

    public void insertIntoDailyProgressesTable(String username, int burnedCalories) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        connection.createStatement().execute(String.format("INSERT INTO DailyProgresses (username, burnedCalories) VALUES ('%s', %d) " + "ON CONFLICT DO UPDATE SET burnedCalories = %d", username, burnedCalories));
        connection.commit();
    }

    public void insertIntoEatenMealsTable(String userName, List<Meal> eatenMeals) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        if (!eatenMeals.isEmpty()) {
            StringBuilder queryBuilder = new StringBuilder(String.format("DELETE FROM EatenMeals WHERE " + "username = %s AND mealName NOT IN (", userName));
            for (Meal meal : eatenMeals) {
                queryBuilder.append(meal.getName()).append(", ");
            }
            queryBuilder.append(" )");
            connection.createStatement().execute(queryBuilder.toString());
        }
        for (int i = 0; i < eatenMeals.size(); i++) {
            Meal eatenMeal = eatenMeals.get(i);
            connection.createStatement().execute(String.format("INSERT INTO EatenMeals (username, mealName, mealNumber, weight)" + " VALUES ('%s','%s',%d,%f)", userName, eatenMeal.getName(), i, eatenMeal.getWeight()));
        }
        connection.commit();
    }

    public void insertIntoGroceriesItemTable(String userName, GroceriesCheckList checkList) throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        insertGroceriesIngredient(checkList.getNeededIngredients(), connection, userName, "NEEDED");
        insertGroceriesIngredient(checkList.getMissingIngredients(), connection, userName, "MISSING");
        insertGroceriesIngredient(checkList.getTakenIngredients(), connection, userName, "TAKEN");
    }

    private void insertGroceriesIngredient(Set<Ingredient> checkList, Connection connection, String userName, String status) throws SQLException {
        if (checkList.isEmpty())
            return;
        StringBuilder queryBuilder = new StringBuilder(String.format("DELETE FROM GroceriesItems WHERE " + "username=%s AND status = %s AND ingredientName NOT IN (", userName, status));
        int i = 0;
        for (Ingredient ingredient : checkList) {
            queryBuilder.append(ingredient.getName());
            i++;
            if (i < checkList.size())
                queryBuilder.append(", ");
        }
        queryBuilder.append(" )");
        for (Ingredient neededIngredient : checkList) {
            connection.createStatement().execute(String.format("INSERT INTO GroceriesItems " + "(username, ingredientName, quantity, status) VALUES ('%s','%s',%f,'%s')", userName, neededIngredient.getName(), neededIngredient.getQuantity().getAmount(), status));
        }
    }

    public void deleteFromIngredientsTable(String ingredientName) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM Ingredients WHERE ingredientName = '%s'", ingredientName));
        }
    }

    public void deleteFromMealsTable(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM Meals WHERE mealName = '%s'", mealName));
        }
    }

    public void deleteFromMealCompositionsTable(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM MealCompositions WHERE mealName = '%s'", mealName));
        }
    }

    public void deleteFromUserTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM User WHERE username = '%s", username));
        }
    }

    public void deleteFromMealPlanTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM MealPlan WHERE username = '%s", username));
        }
    }

    public void deleteFromDailyMealsTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM DailyMeals WHERE username = '%s", username));
        }
    }

    public void deleteFromDailyProgressesTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM DailyProgresses WHERE username = '%s", username));
        }
    }

    public void deleteFromEatenMealsTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM EatenMeals WHERE username = '%s", username));
        }
    }

    public void insertIntoGroceriesItemTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format("DELETE FROM GroceriesItems WHERE username = '%s", username));
        }
    }

    public ResultSet selectFromIngredientTable(String ingredientName) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT * FROM Ingredients WHERE ingredientName = '%s'", ingredientName));
        }
        return resultSet;
    }

    public ResultSet selectFromUserTable() throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM User");
        }
        return resultSet;
    }

    public ResultSet selectFromMealsJoinedMealCompositionsTable(String mealName) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT MealCompositions.* FROM " + "Meals JOIN MealCompositions ON Meals.mealName = MealCompositions.mealName WHERE" + "mealName = '%s'", mealName));
        }
        return resultSet;
    }

    public ResultSet selectFromDailyProgressesTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT * FROM DailyProgresses WHERE username = '%s'", username));
        }
        return resultSet;
    }

    public ResultSet selectFromEatenMealsTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT * FROM EatenMeals WHERE username = '%s'", username));
        }
        return resultSet;
    }

    public ResultSet selectFromMealPlanTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT * FROM MealPlan WHERE username = '%s'", username));
        }
        return resultSet;
    }

    public ResultSet selectFromDailyMealsTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT * FROM DailyMeals WHERE username = '%s'", username));
        }
        return resultSet;
    }

    public ResultSet selectFromGroceriesItemTable(String username) throws SQLException {
        Connection connection = DBManager.getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(String.format("SELECT * FROM GroceriesItem WHERE username = '%s'", username));
        }
        return resultSet;
    }
}
