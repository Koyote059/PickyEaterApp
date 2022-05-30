package pickyeater.database;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Quantity;
import pickyeater.database.SQLutils.*;
import pickyeater.utils.IngredientQuantityConverter;

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
            if(DBChecker.isWordIllegal(ingredient.getName())) throw new SQLException("Illegal name: " + ingredient.getName());

            Quantity quantity = ingredient.getQuantity();
            Ingredient savingIngredient;


            if(quantity.getAmount()!=100){
                IngredientQuantityConverter converter = new IngredientQuantityConverter();
                savingIngredient =  converter.convert(ingredient,100);
            } else savingIngredient = ingredient;

            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.insertIntoIngredientsTable(savingIngredient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ingredient> loadIngredient(String ingredientName) {
        try {
            if(DBChecker.isWordIllegal(ingredientName)) throw new SQLException("Illegal name: " + ingredientName);
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
            if(DBChecker.isWordIllegal(ingredientName)) throw new SQLException("Illegal name: " + ingredientName);
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            ResultSet resultSet = executor.selectIngredient(ingredientName);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Ingredient> loadEveryIngredient() { // Todo check if name is legal
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
            if(DBChecker.isWordIllegal(name)) throw new SQLException("Illegal name: " + name);
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
    public boolean isIngredientUsed(Ingredient ingredient) {
        try {
            if(DBChecker.isWordIllegal(ingredient.getName())) throw new SQLException("Illegal name: " + ingredient.getName());
            SQLSafeQueryExecutor executor = queryExecutor.getSafeQueryExecutor();
            ResultSet resultSet = executor.findIngredientInTables(ingredient.getName());
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteIngredient(Ingredient ingredient) {
        try {
            if(DBChecker.isWordIllegal(ingredient.getName())) throw new SQLException("Illegal name: " + ingredient.getName());
            SQLUnSafeQueryExecutor executor = queryExecutor.getUnSafeQueryExecutor();
            executor.deleteFromIngredientsTable(ingredient.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
