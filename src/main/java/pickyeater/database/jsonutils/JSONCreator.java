//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.database.jsonutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.Quantity;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;
import pickyeater.basics.user.UserGoal;
import pickyeater.basics.user.UserStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JSONCreator {
    public JSONCreator() {
    }

    public static JSONObject getMealJsonObject(Meal meal) throws JSONException {
        JSONObject mealJSON = new JSONObject();
        mealJSON.put("Name", meal.getName());
        mealJSON.put("Ingredients", getIngredientsJsonObject(meal.getIngredients()));
        return mealJSON;
    }

    private static JSONArray getMealsJsonObject(Collection<Meal> meals) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        Iterator var2 = meals.iterator();

        while(var2.hasNext()) {
            Meal meal = (Meal)var2.next();
            jsonArray.put(getMealJsonObject(meal));
        }

        return jsonArray;
    }

    public static JSONObject getQuantityJsonObject(Quantity quantity) throws JSONException {
        JSONObject quantityJSON = new JSONObject();
        quantityJSON.put("Quantity", quantity.getQuantity());
        quantityJSON.put("QuantityType", quantity.getQuantityType().name());
        quantityJSON.put("QuantityGrams", quantity.getQuantityGrams());
        return quantityJSON;
    }

    public static JSONObject getNutrientsJsonObject(Nutrients nutrients) throws JSONException {
        JSONObject nutrientsJSON = new JSONObject();
        nutrientsJSON.put("SimpleCarbs", nutrients.getSimpleCarbs());
        nutrientsJSON.put("ComplexCarbs", nutrients.getComplexCarbs());
        nutrientsJSON.put("Fibers", nutrients.getFibers());
        nutrientsJSON.put("Proteins", nutrients.getProteins());
        nutrientsJSON.put("SaturatedFats", nutrients.getSaturatedFats());
        nutrientsJSON.put("UnSaturatedFats", nutrients.getUnSaturatedFats());
        nutrientsJSON.put("TransFats", nutrients.getTransFats());
        nutrientsJSON.put("Alcohol", nutrients.getAlcohol());
        return nutrientsJSON;
    }

    public static JSONArray getIngredientsJsonObject(Set<Ingredient> ingredients) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        Iterator var2 = ingredients.iterator();

        while(var2.hasNext()) {
            Ingredient ingredient = (Ingredient)var2.next();
            jsonArray.put(getIngredientJsonObject(ingredient));
        }

        return jsonArray;
    }

    public static JSONObject getIngredientJsonObject(Ingredient ingredient) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", ingredient.getName());
        jsonObject.put("Quantity", getQuantityJsonObject(ingredient.getQuantity()));
        jsonObject.put("Tags", ingredient.getTags());
        jsonObject.put("Nutrients", getNutrientsJsonObject(ingredient.getNutrients()));
        jsonObject.put("Price", ingredient.getPrice());
        return jsonObject;
    }

    public static JSONObject getJsonObjectFromFile(String filePath) {
        try {
            String fileContent = Files.readString(Path.of(filePath));
            return fileContent.isBlank() ? new JSONObject() : new JSONObject(fileContent);
        } catch (JSONException | IOException var2) {
            var2.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static JSONObject getUserStatusJsonObject(UserStatus userStatus) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Bodyfat", userStatus.getBodyFat());
        jsonObject.put("Sex", userStatus.getSex().name());
        jsonObject.put("Weight", userStatus.getWeight());
        jsonObject.put("Height", userStatus.getHeight());
        jsonObject.put("Birth", userStatus.getDateOfBirth().getTime());
        return jsonObject;
    }

    public static JSONObject getUserGoalJsonObject(UserGoal userGoal) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("LifeStyle", userGoal.getLifeStyle().name());
        jsonObject.put("WeightVariationGoal", userGoal.getWeightVariationGoal());
        return jsonObject;
    }

    public static JSONObject getDailyProgressesJsonObject(DailyProgresses dailyProgresses) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BurnedCalories", dailyProgresses.getBurnedCalories());
        jsonObject.put("EatenMeals", getMealsJsonObject(dailyProgresses.getEatenMeals()));
        return jsonObject;
    }

    public static JSONObject getMealPlanJsonObjcet(MealPlan mealPlan) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DailyMealPlans", getDailyMealPlansJsonObject(mealPlan.getDailyMealPlans()));
        return jsonObject;
    }

    public static JSONArray getDailyMealPlansJsonObject(List<DailyMealPlan> dailyMealPlans) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        Iterator var2 = dailyMealPlans.iterator();

        while(var2.hasNext()) {
            DailyMealPlan dailyMealPlan = (DailyMealPlan)var2.next();
            jsonArray.put(getMealsJsonObject(dailyMealPlan.getMeals()));
        }

        return jsonArray;
    }

    public static JSONObject getUserJsonObject(User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", user.getName());
        jsonObject.put("UserGoal", getUserGoalJsonObject(user.getUserGoal()));
        jsonObject.put("UserStatus", getUserStatusJsonObject(user.getUserStatus()));
        jsonObject.put("DailyProgresses", getDailyProgressesJsonObject(user.getDailyProgresses()));
        Optional<MealPlan> mealPlan = user.getMealPlan();
        if (mealPlan.isPresent()) {
            jsonObject.put("MealPlan", getMealPlanJsonObjcet((MealPlan)mealPlan.get()));
        } else {
            jsonObject.put("MealPlan", "");
        }

        return jsonObject;
    }
}
