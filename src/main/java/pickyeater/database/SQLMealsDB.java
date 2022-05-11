package pickyeater.database;

import pickyeater.basics.food.Meal;
import pickyeater.database.SQLutils.SQLExecutorManager;
import pickyeater.database.SQLutils.SQLCreator;
import pickyeater.database.SQLutils.SQLSafeQueryExecutor;
import pickyeater.database.SQLutils.SQLUnSafeQueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SQLMealsDB implements MealsDatabase {

    private  final SQLExecutorManager queryExecutor;

    public SQLMealsDB(SQLExecutorManager queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    @Override
    public void saveMeal(Meal meal)  {
        try {
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.insertIntoMealsTable(meal.getName());
            executor.insertIntoMealCompositionsTable(meal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Meal> loadMeal(String mealName) {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            SQLCreator sqlCreator = new SQLCreator();
            ResultSet resultSet = executor.selectMeal(mealName);
            List<Meal> meals = sqlCreator.getMeals(resultSet);
            if(meals.isEmpty()) return Optional.empty();
            return Optional.ofNullable(meals.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasMeal(String mealName) {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            ResultSet resultSet = executor.selectMeal(mealName);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Meal> loadEveryMeal() {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            SQLCreator sqlCreator = new SQLCreator();
            ResultSet resultSet = executor.selectEveryMeal();
            List<Meal> meals = sqlCreator.getMeals(resultSet);
            return new HashSet<>(meals);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Meal> getMealsThatStartWith(String name) {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            SQLCreator sqlCreator = new SQLCreator();
            ResultSet resultSet = executor.selectMealsThatStartWith(name);
            List<Meal> meals = sqlCreator.getMeals(resultSet);
            return new HashSet<>(meals);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMeal(Meal meal) {
        try {
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.deleteFromMealsTable(meal.getName());
            executor.deleteFromMealCompositionsTable(meal.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
