package pickyeater.database;

import pickyeater.basics.food.Meal;
import pickyeater.database.SQLutils.*;
import pickyeater.utils.MealQuantityConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SQLMealsDB implements MealsDatabase {
    private final SQLExecutorManager queryExecutor;

    public SQLMealsDB(SQLExecutorManager queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    @Override
    public void saveMeal(Meal meal) {
        try {
            if (DBChecker.isWordIllegal(meal.getName()))
                throw new SQLException("Illegal name: " + meal.getName());
            if (meal.getIngredients().size() == 0)
                throw new RuntimeException("Cannot store an empty meal!");
            Meal savingMeal;
           /* if (meal.getWeight() != 100) {
                MealQuantityConverter converter = new MealQuantityConverter();
                savingMeal = converter.convert(meal, 100); // TODO REMOVE ALL IF
            } else */
            savingMeal = meal;
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.insertIntoMealsTable(savingMeal.getName());
            executor.insertIntoMealCompositionsTable(savingMeal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Meal> loadMeal(String mealName) {
        try {
            if (DBChecker.isWordIllegal(mealName))
                throw new SQLException("Illegal name: " + mealName);
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            SQLCreator sqlCreator = new SQLCreator();
            ResultSet resultSet = executor.selectMeal(mealName);
            List<Meal> meals = sqlCreator.getMeals(resultSet);
            if (meals.isEmpty())
                return Optional.empty();
            return Optional.ofNullable(meals.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasMeal(String mealName) {
        try {
            if (DBChecker.isWordIllegal(mealName))
                throw new SQLException("Illegal name: " + mealName);
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
            if (DBChecker.isWordIllegal(name))
                throw new SQLException("Illegal name: " + name);
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
    public boolean isMealUsed(Meal meal) {
        try {
            if (DBChecker.isWordIllegal(meal.getName()))
                throw new SQLException("Illegal name: " + meal.getName());
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            ResultSet resultSet = executor.findMealInTables(meal.getName());
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMeal(Meal meal) {
        try {
            if (DBChecker.isWordIllegal(meal.getName()))
                throw new SQLException("Illegal name: " + meal.getName());
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.deleteFromMealsTable(meal.getName());
            executor.deleteFromMealCompositionsTable(meal.getName()); // Useless, in theory. Cascade should do it for us.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
