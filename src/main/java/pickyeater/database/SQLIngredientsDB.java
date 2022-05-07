package pickyeater.database;

import pickyeater.basics.food.Ingredient;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class SQLIngredientsDB implements IngredientsDatabase {

    private  final SQLQueryExecutor queryExecutor;

    public SQLIngredientsDB(String path){
        this.queryExecutor = new SQLQueryExecutor(path);
    }

    @Override
    public void saveIngredient(Ingredient ingredient) {
        try {
            queryExecutor.insertIntoIngredientsTable(ingredient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ingredient> loadIngredient(String ingredientName) {
        return Optional.empty();
    }

    @Override
    public boolean hasIngredient(String ingredientName) {
        return false;
    }

    @Override
    public Set<Ingredient> loadEveryIngredient() {
        return null;
    }

    @Override
    public Set<Ingredient> getIngredientsThatStartWith(String string) {
        return null;
    }
}
