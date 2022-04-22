//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.database;

import database.database.jsonutils.FoodCreator;
import database.database.jsonutils.JSONCreator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pickyeater.food.Ingredient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class JSONIngredientsDatabase implements IngredientsDatabase {
    private final String dbPath;

    public JSONIngredientsDatabase(String dbPath) {
        this.dbPath = dbPath;
    }

    public void saveIngredient(Ingredient ingredient) {
        try {
            File file = new File(this.dbPath);
            if (!file.exists()) {
                file.createNewFile();
            }

            JSONObject dbJSON = JSONCreator.getJsonObjectFromFile(this.dbPath);
            if (dbJSON.has(ingredient.getName())) {
                dbJSON.remove(ingredient.getName());
            }

            dbJSON.append(ingredient.getName(), JSONCreator.getIngredientJsonObject(ingredient));
            Files.writeString(Path.of(this.dbPath), dbJSON.toString());
        } catch (JSONException | IOException var4) {
            var4.printStackTrace();
            throw new RuntimeException(var4);
        }
    }

    public Optional<Ingredient> loadIngredient(String ingredientName) {
        try {
            if (!Files.exists(Path.of(this.dbPath), new LinkOption[0])) {
                return Optional.empty();
            } else {
                JSONObject jsonObject = JSONCreator.getJsonObjectFromFile(this.dbPath);
                if (!jsonObject.has(ingredientName)) {
                    return Optional.empty();
                } else {
                    JSONArray ingredientJsonObject = (JSONArray)jsonObject.get(ingredientName);
                    Ingredient ingredient = FoodCreator.getIngredientFromJSON(ingredientJsonObject.getJSONObject(0));
                    return Optional.of(ingredient);
                }
            }
        } catch (JSONException var5) {
            var5.printStackTrace();
            throw new RuntimeException();
        }
    }

    public boolean hasIngredient(String ingredientName) {
        if (!Files.exists(Path.of(this.dbPath), new LinkOption[0])) {
            return false;
        } else {
            JSONObject jsonObject = JSONCreator.getJsonObjectFromFile(this.dbPath);
            return jsonObject.has(ingredientName);
        }
    }

    public Set<Ingredient> loadEveryIngredient() {
        Set<Ingredient> ingredients = new HashSet();

        try {
            if (!Files.exists(Path.of(this.dbPath), new LinkOption[0])) {
                return ingredients;
            } else {
                JSONObject jsonObject = JSONCreator.getJsonObjectFromFile(this.dbPath);
                JSONArray names = jsonObject.names();

                for(int i = 0; i < names.length(); ++i) {
                    String ingredientName = names.getString(i);
                    JSONObject ingredientJSONObject = ((JSONArray)jsonObject.get(ingredientName)).getJSONObject(0);
                    Ingredient ingredient = FoodCreator.getIngredientFromJSON(ingredientJSONObject);
                    ingredients.add(ingredient);
                }

                return ingredients;
            }
        } catch (JSONException var8) {
            var8.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Set<Ingredient> getIngredientsThatStartWith(String string) {
        Set<Ingredient> ingredients = this.loadEveryIngredient();
        ingredients.removeIf((ingredient) -> {
            return !ingredient.getName().startsWith(string);
        });
        return ingredients;
    }
}
