package pickyeater.database;

import pickyeater.basics.food.Ingredient;
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

public class SQLIngredientsDB implements IngredientsDatabase {

    private  final SQLExecutorManager queryExecutor;

    public SQLIngredientsDB(SQLExecutorManager queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    @Override
    public void saveIngredient(Ingredient ingredient) {
        try {
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.insertIntoIngredientsTable(ingredient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ingredient> loadIngredient(String ingredientName) {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            SQLCreator sqlCreator = new SQLCreator();
            ResultSet resultSet = executor.selectIngredient(ingredientName);
            List<Ingredient> ingredientList = sqlCreator.getIngredients(resultSet);
            if(ingredientList.isEmpty()) return Optional.empty();
            return Optional.ofNullable(ingredientList.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasIngredient(String ingredientName) {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            ResultSet resultSet = executor.selectIngredient(ingredientName);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Ingredient> loadEveryIngredient() {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            SQLCreator sqlCreator = new SQLCreator();
            ResultSet resultSet = executor.selectEveryIngredient();
            List<Ingredient> ingredientList = sqlCreator.getIngredients(resultSet);
            return new HashSet<>(ingredientList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Ingredient> getIngredientsThatStartWith(String name) {
        try {
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            SQLCreator sqlCreator = new SQLCreator();
            ResultSet resultSet = executor.selectIngredientsThatStartWith(name);
            List<Ingredient> ingredientList = sqlCreator.getIngredients(resultSet);
            return new HashSet<>(ingredientList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteIngredient(Ingredient ingredient) {
        try {
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.deleteFromIngredientsTable(ingredient.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
