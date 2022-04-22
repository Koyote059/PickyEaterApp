package pickyeater.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pickyeater.database.jsonutils.FoodCreator;
import pickyeater.database.jsonutils.JSONCreator;
import pickyeater.food.Meal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class JSONMealsDatabase implements MealsDatabase {

    private final String dbPath;

    public JSONMealsDatabase(String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public void saveMeal(Meal meal) {

        try{
            File file = new File(dbPath);
            if(!file.exists()) file.createNewFile();
            JSONObject dbJSON = JSONCreator.getJsonObjectFromFile(dbPath);
            if(dbJSON.has(meal.getName())) dbJSON.remove(meal.getName());
            dbJSON.append(meal.getName(),JSONCreator.getMealJsonObject(meal));
            Files.writeString(Path.of(dbPath),dbJSON.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Meal> loadMeal(String mealName)  {
        try {
            if(!Files.exists(Path.of(dbPath))) return Optional.empty();
            JSONObject jsonObject = JSONCreator.getJsonObjectFromFile(dbPath);
            if(!jsonObject.has(mealName)) return Optional.empty();
            JSONObject mealJsonObject = ((JSONArray) jsonObject.get(mealName)).getJSONObject(0);
            Meal meal = FoodCreator.getMealFromJson(mealJsonObject);
            return Optional.of(meal);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public boolean hasMeal(String mealName) {
        if(!Files.exists(Path.of(dbPath))) return false;
        JSONObject jsonObject = JSONCreator.getJsonObjectFromFile(dbPath);
        return jsonObject.has(mealName);
    }

    @Override
    public Set<Meal> loadEveryMeal() {
        Set<Meal> meals = new HashSet<>();
        try {
            if(!Files.exists(Path.of(dbPath))) return meals;
            JSONObject jsonObject = JSONCreator.getJsonObjectFromFile(dbPath);
            JSONArray names = jsonObject.names();
            for(int i = 0; i<names.length(); i++){
                String mealName = names.getString(i);
                JSONObject mealJsonObject = ((JSONArray) jsonObject.get(mealName)).getJSONObject(0);
                Meal meal = FoodCreator.getMealFromJson(mealJsonObject);
                meals.add(meal);
            }
            return meals;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Set<Meal> getMealsThatStartWith(String string) {
        Set<Meal> everyMeal = loadEveryMeal();
        everyMeal.removeIf(meal -> !meal.getName().startsWith(string));
        return everyMeal;
    }

}
