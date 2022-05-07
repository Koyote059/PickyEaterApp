package pickyeater.database;

import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;

import java.sql.SQLException;
import java.util.Optional;

public class SQLUserDB implements UserDatabase {

    private  final SQLQueryExecutor queryExecutor;

    public SQLUserDB(String path){
        this.queryExecutor = new SQLQueryExecutor(path);
    }
    @Override
    public Optional<User> loadUser() {
        return Optional.empty();
    }

    @Override
    public void saveUser(User user) {
        try {
            queryExecutor.inertIntoUserTable(user);
            Optional<MealPlan> mealPlanOptional = user.getMealPlan();
            if(mealPlanOptional.isPresent()) {
                MealPlan mealPlan = mealPlanOptional.get();
                queryExecutor.insertIntoMealPlanTable(user.getName(),mealPlan.getBeginningDay());
                queryExecutor.insertIntoDailyMealsTable(user.getName(),mealPlan.getDailyMealPlans());
            } else {
                queryExecutor.deleteFromMealPlanTable(user.getName());
                queryExecutor.deleteFromDailyMealsTable(user.getName());
            }
            DailyProgresses dailyProgresses = user.getDailyProgresses();
            queryExecutor.insertIntoDailyProgressesTable(user.getName(),dailyProgresses.getBurnedCalories());
            queryExecutor.insertIntoEatenMealsTable(user.getName(),dailyProgresses.getEatenMeals());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
