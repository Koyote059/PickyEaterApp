package pickyeater.database;

import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;
import pickyeater.database.SQLutils.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SQLUserDB implements UserDatabase {
    private final SQLExecutorManager queryExecutor;

    public SQLUserDB(SQLExecutorManager queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    @Override
    public Optional<User> loadUser() {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            SQLCreator sqlCreator = new SQLCreator();
            ResultSet userRS = executor.selectFromUserTable();
            if (!userRS.next())
                return Optional.empty();
            String userName = userRS.getString("username");
            userRS.previous();
            ResultSet dailyProgressesRS = executor.selectFromDailyProgressesTable(userName);
            ResultSet eatenMealsRS = executor.selectFromEatenMealsTable(userName);
            ResultSet mealPlanRS = executor.selectFromMealPlanTable(userName);
            ResultSet dailyMealsRS = executor.selectDailyMeals(userName);
            return sqlCreator.createUser(userRS, dailyProgressesRS, eatenMealsRS, mealPlanRS, dailyMealsRS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            if (DBChecker.isWordIllegal(user.getName()))
                throw new SQLException("Illegal name: " + user.getName());
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.inertIntoUserTable(user);
            Optional<MealPlan> mealPlanOptional = user.getMealPlan();
            if (mealPlanOptional.isPresent()) {
                MealPlan mealPlan = mealPlanOptional.get();
                executor.insertIntoMealPlanTable(user.getName(), mealPlan.getBeginningDay());
                executor.insertIntoDailyMealsTable(user.getName(), mealPlan.getDailyMealPlans());
            } else {
                executor.deleteFromMealPlanTable(user.getName());
                executor.deleteFromDailyMealsTable(user.getName());
            }
            DailyProgresses dailyProgresses = user.getDailyProgresses();
            executor.insertIntoDailyProgressesTable(user.getName(), dailyProgresses);
            executor.insertIntoEatenMealsTable(user.getName(), dailyProgresses.getEatenMeals());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            if (DBChecker.isWordIllegal(user.getName()))
                throw new SQLException("Illegal name: " + user.getName());
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.deleteFromUserTable(user.getName());
            //executor.deleteFromDailyMealsTable(user.getName()); In teoria questi 4
            //executor.deleteFromEatenMealsTable(user.getName()); non dovrebbero servire
            //executor.deleteFromMealPlanTable(user.getName());
            //executor.deleteFromDailyProgressesTable(user.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMealPlan(User user) {
        try {
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.deleteFromDailyMealsTable(user.getName());
            executor.deleteFromMealPlanTable(user.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
