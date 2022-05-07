package pickyeater.database;

import pickyeater.basics.food.Meal;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class SQLMealsDB implements MealsDatabase {

    private  final SQLQueryExecutor queryExecutor;

    public SQLMealsDB(String path){
        this.queryExecutor = new SQLQueryExecutor(path);
    }

    @Override
    public void saveMeal(Meal meal)  {
        try {
            queryExecutor.insertIntoMealsTable(meal.getName());
            queryExecutor.insertIntoMealCompositionsTable(meal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Meal> loadMeal(String mealName) {
        return Optional.empty();
    }

    @Override
    public boolean hasMeal(String mealName) {
        return false;
    }

    @Override
    public Set<Meal> loadEveryMeal() {
        return null;
    }

    @Override
    public Set<Meal> getMealsThatStartWith(String string) {
        return null;
    }
}
