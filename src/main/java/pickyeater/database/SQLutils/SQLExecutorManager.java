package pickyeater.database.SQLutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecutorManager {
    public SQLExecutorManager(String dbPath) {
        DBManager.setConnection(DBManager.JDBC_Driver_SQLite, String.format("jdbc:sqlite:%s", dbPath));
    }

    public void createTables() throws SQLException {
        Connection connection = DBManager.getConnection();
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Ingredients (\n" + "ingredientName VARCHAR(32),\n" + "price SMALLMONEY,\n" + "quantity FLOAT,\n" + "quantityType VARCHAR(16),\n" + "gramsPerQuantity REAL," + "complexCarbs REAL,\n" + "simpleCarbs REAL,\n" + "fibers REAL,\n" + "saturatedFats REAL,\n" + "unSaturatedFats REAL,\n" + "transFats REAL,\n" + "proteins REAL,\n" + "alcohol REAL,\n" + "CONSTRAINT ILLEGAL_ARGUMENT_QUANTITYTYPE CHECK (quantityType = 'GRAMS' OR quantityType = 'MILLILITERS' OR quantityType = 'PIECES'),\n" + "CONSTRAINT PK_INGREDIENTS PRIMARY KEY (ingredientName)\n" + ");");
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Meals (\n" + "    mealName VARCHAR(32),\n" + "    CONSTRAINT PK_MEALS PRIMARY KEY (mealName)\n" + ")");
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS MealCompositions (\n" + "    ingredientName VARCHAR(32),\n" + "    mealName VARCHAR(32),\n" + "    quantity REAL,\n" + "    CONSTRAINT PK_MEALCOMPOSITIONS PRIMARY KEY (ingredientName,mealName),\n" + "    CONSTRAINT FK_MEALCOMPOSITIONS_INGREDIENTNAME FOREIGN KEY (ingredientName) REFERENCES Ingredients, \n" + "    CONSTRAINT FK_MEALCOMPOSITIONS_MEALNAME FOREIGN KEY (mealName) REFERENCES Meals ON DELETE CASCADE \n" + ")");
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS User (\n" + "    username VARCHAR(32),\n" + "    sex VARCHAR(8),\n" + "    bodyFat REAL,\n" + "    height SMALLINT,\n" + "    weight REAL,\n" + "    dateOfBirth LONG,\n" + "    weightGoal VARCHAR(16),\n" + "    lifeStyle VARCHAR(16),\n" + "    neededCarbs REAL,\n" + "    neededFats REAL,\n" + "    neededProteins REAL,\n" + "    CONSTRAINT PK_USER PRIMARY KEY (username),\n" + "    CONSTRAINT ILLEGAL_ARGUMENT_SEX CHECK ( sex = 'MALE' OR sex = 'FEMALE'),\n" + "    CONSTRAINT ILLEGAL_ARGUMENT_WEIGHTGOAL CHECK\n" + "        ( weightGoal = 'LOSE_WEIGHT' OR weightGoal = 'MAINTAIN_WEIGHT' OR weightGoal = 'INCREASE_WEIGHT'  ),\n" + "    CONSTRAINT ILLEGAL_ARGUMENT_LIFESTYLE CHECK\n" + "        ( lifeStyle = 'SEDENTARY' OR lifeStyle = 'LIGHTLY_ACTIVE' OR lifeStyle = 'ACTIVE' OR lifeStyle = 'VERY_ACTIVE')\n" + ")");
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS MealPlan(\n" + "    username VARCHAR(32),\n" + "    beginningDay LONG,\n" + "    CONSTRAINT PK_MEALPLAN PRIMARY KEY (username),\n" + "    CONSTRAINT FK_MEALPLAN_USERNAME FOREIGN KEY (username) REFERENCES User ON DELETE CASCADE\n" + ")");
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS DailyMeals (\n" + "    mealName VARCHAR(32),\n" + "    username VARCHAR(32),\n" + "    dayNumber TINYINT,\n" + "    mealNumber TINYINT,\n" + "    ingredientsRatio REAL,\n" + // ingredientsRatio = ingredientWeightInDailyMeals/ingredientWeightInMeals
                    "    CONSTRAINT PK_DAILYMEALS PRIMARY KEY (mealName,username,dayNumber,mealNumber),\n" + "    CONSTRAINT FK_DAILYMEALS_MEALNAME FOREIGN KEY (mealName) REFERENCES Meals, " + "    CONSTRAINT FK_DAILYMEALS_USERNAME FOREIGN KEY (username) REFERENCES MealPlan ON DELETE CASCADE\n" + ")");
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS DailyProgresses (\n" + "    username VARCHAR(32),\n" + "    burnedCalories SMALLINT,\n" + "    date SMALLINT,\n" + "    CONSTRAINT PK_DAILYPROGRESSES PRIMARY KEY (username),\n" + "    CONSTRAINT FK_DAILYPROGRESSES_USERNAME FOREIGN KEY (username) REFERENCES User ON DELETE CASCADE\n" + ")");
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS GroceriesItems (\n" + "    username VARCHAR(32),\n" + "    ingredientName VARCHAR(32),\n" + "    quantity REAL,\n" + "    status VARCHAR(16),\n" + "    CONSTRAINT PK_DAILYPROGRESSES PRIMARY KEY (username,ingredientName),\n" + "    CONSTRAINT FK_DAILYPROGRESSES_USERNAME FOREIGN KEY (username) REFERENCES User ON DELETE CASCADE,\n" + "    CONSTRAINT FK_DAILYPROGRESSES_INGREDIENTNAME FOREIGN KEY (ingredientName) REFERENCES Ingredients,\n" + "   CONSTRAINT ILLEGAL_ARGUMENT_STATUS CHECK (status = 'MISSING' OR status = 'NEEDED' OR status = 'TAKEN')" + ")");
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS EatenMeals (\n" + "    username VARCHAR(32),\n" + "    mealName VARCHAR(32),\n" + "    mealNumber TINYINT,\n" + "    ingredientsRatio REAL,\n" + // ingredientsRatio = ingredientWeightInEatenMeals/ingredientWeightInMeals
                    "    CONSTRAINT PK_DAILYPROGRESSES PRIMARY KEY (username,mealName,mealNumber),\n" + "    CONSTRAINT FK_EATENMEALS FOREIGN KEY (mealName) REFERENCES Meals,\n" + "    CONSTRAINT FK_DAILYPROGRESSES_USERNAME FOREIGN KEY (username) REFERENCES DailyProgresses ON DELETE CASCADE\n" + ")");
        }
        connection.commit();
    }

    public SQLSafeQueryExecutor getSafeQueryExecutor() {
        return new SQLSafeQueryExecutor();
    }

    public SQLUnSafeQueryExecutor getUnSafeQueryExecutor() {
        return new SQLUnSafeQueryExecutor();
    }
}
