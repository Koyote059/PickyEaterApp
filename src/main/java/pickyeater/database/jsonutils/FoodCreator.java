//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.database.jsonutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pickyeater.builders.IngredientBuilder;
import pickyeater.builders.MealBuilder;
import pickyeater.builders.PickyIngredientBuilder;
import pickyeater.builders.PickyMealBuilder;
import pickyeater.basics.food.*;
import pickyeater.basics.user.*;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.mealplan.PickyDailyMealPlan;
import pickyeater.basics.mealplan.PickyMealPlan;

import java.util.Date;
import java.util.List;

public class FoodCreator {
    public FoodCreator() {
    }

    public static Meal getMealFromJson(JSONObject mealJSON) throws JSONException {
        MealBuilder mealBuilder = new PickyMealBuilder();
        mealBuilder.setName(mealJSON.getString("Name"));
        mealBuilder.addIngredients(getIngredientsFromJSON(mealJSON.getJSONArray("Ingredients")));
        return mealBuilder.build();
    }

    private static Meal[] getMealsFromJSON(JSONArray eatenMeals) throws JSONException {
        Meal[] meals = new Meal[eatenMeals.length()];

        for(int i = 0; i < meals.length; ++i) {
            meals[i] = getMealFromJson(eatenMeals.getJSONObject(i));
        }

        return meals;
    }

    public static Ingredient[] getIngredientsFromJSON(JSONArray ingredientsJSON) throws JSONException {
        Ingredient[] ingredients = new Ingredient[ingredientsJSON.length()];

        for(int i = 0; i < ingredients.length; ++i) {
            ingredients[i] = getIngredientFromJSON(ingredientsJSON.getJSONObject(i));
        }

        return ingredients;
    }

    public static Ingredient getIngredientFromJSON(JSONObject ingredientJSON) throws JSONException {
        IngredientBuilder ingredientBuilder = new PickyIngredientBuilder();
        ingredientBuilder.setName(ingredientJSON.getString("Name"));
        ingredientBuilder.setPrice(ingredientJSON.getDouble("Price"));
        ingredientBuilder.setNutrients(getNutrientsFromJSON(ingredientJSON.getJSONObject("Nutrients")));
        ingredientBuilder.addTags(getTagsFromJSON(ingredientJSON.getJSONArray("Tags")));
        ingredientBuilder.setQuantity(getQuantityFromJSON(ingredientJSON.getJSONObject("Quantity")));
        return ingredientBuilder.build();
    }

    public static Quantity getQuantityFromJSON(JSONObject quantityJSON) throws JSONException {
        double quantity = quantityJSON.getDouble("Quantity");
        QuantityType quantityType = QuantityType.valueOf(quantityJSON.getString("QuantityType"));
        double quantityGrams = quantityJSON.getDouble("QuantityGrams");
        return new PickyQuantity(quantity, quantityType, quantityGrams);
    }

    public static Nutrients getNutrientsFromJSON(JSONObject nutrientsJSON) throws JSONException {
        double proteins = nutrientsJSON.getDouble("Proteins");
        double complexCarbs = nutrientsJSON.getDouble("ComplexCarbs");
        double simpleCarbs = nutrientsJSON.getDouble("SimpleCarbs");
        double fibers = nutrientsJSON.getDouble("Fibers");
        double saturatedFats = nutrientsJSON.getDouble("SaturatedFats");
        double unSaturatedFats = nutrientsJSON.getDouble("UnSaturatedFats");
        double transFats = nutrientsJSON.getDouble("TransFats");
        double alcohol = nutrientsJSON.getDouble("Alcohol");
        return new PickyNutrients(proteins, complexCarbs, simpleCarbs, fibers, saturatedFats, unSaturatedFats, transFats, alcohol);
    }

    public static String[] getTagsFromJSON(JSONArray tags) throws JSONException {
        String[] tagsArray = new String[tags.length()];

        for(int i = 0; i < tags.length(); ++i) {
            tagsArray[i] = (String)tags.get(i);
        }

        return tagsArray;
    }

    public static User getUserFromJSON(JSONObject userJSON) throws JSONException {
        String name = userJSON.getString("Name");
        UserStatus userStatus = getUserStatusFromJSON(userJSON.getJSONObject("UserStatus"));
        DailyProgresses dailyProgresses = getDailyProgressesFromJSON(userJSON.getJSONObject("DailyProgresses"));
        UserGoal userGoal = getUserGoalFromJSON(userJSON.getJSONObject("UserGoal"));
        Object mealPlanJSON = userJSON.get("MealPlan");
        MealPlan mealPlan;
        if (mealPlanJSON instanceof String) {
            mealPlan = null;
        } else {
            mealPlan = getMealPlanFromJSON((JSONObject)mealPlanJSON);
        }

        return new PickyUser(name, userStatus, userGoal, dailyProgresses, mealPlan);
    }

    private static UserStatus getUserStatusFromJSON(JSONObject userStatusJSON) throws JSONException {
        double weight = userStatusJSON.getDouble("Weight");
        double bodyFat = userStatusJSON.getDouble("Bodyfat");
        double height = userStatusJSON.getDouble("Height");
        Date dateOfBirth = new Date(userStatusJSON.getLong("Birth"));
        Sex sex = Sex.valueOf(userStatusJSON.getString("Sex"));
        return new PickyUserStatus(weight, height, bodyFat, dateOfBirth, sex);
    }

    private static DailyProgresses getDailyProgressesFromJSON(JSONObject dailyProgressesJSON) throws JSONException {
        int burnedCalories = dailyProgressesJSON.getInt("BurnedCalories");
        List<Meal> eatenMeals = List.of(getMealsFromJSON(dailyProgressesJSON.getJSONArray("EatenMeals")));
        return new PickyDailyProgresses(burnedCalories, eatenMeals);
    }

    private static UserGoal getUserGoalFromJSON(JSONObject userGoalJSON) throws JSONException {
        LifeStyle lifeStyle = LifeStyle.valueOf(userGoalJSON.getString("LifeStyle"));
        double weightVariationGoal = userGoalJSON.getDouble("WeightVariationGoal");
        return new PickyUserGoal(lifeStyle, weightVariationGoal);
    }

    private static MealPlan getMealPlanFromJSON(JSONObject mealPlan) throws JSONException {
        List<DailyMealPlan> dailyMealPlans = List.of(getDailyMealPlansFromJSON(mealPlan.getJSONArray("MealPlans")));
        return new PickyMealPlan(dailyMealPlans);
    }

    private static DailyMealPlan[] getDailyMealPlansFromJSON(JSONArray mealPlans) throws JSONException {
        DailyMealPlan[] dailyMealPlans = new DailyMealPlan[mealPlans.length()];

        for(int i = 0; i < dailyMealPlans.length; ++i) {
            dailyMealPlans[i] = getDailyMealPlanFromJSON(mealPlans.getJSONArray(i));
        }

        return dailyMealPlans;
    }

    private static DailyMealPlan getDailyMealPlanFromJSON(JSONArray mealsJSON) throws JSONException {
        Meal[] meals = new Meal[mealsJSON.length()];

        for(int i = 0; i < meals.length; ++i) {
            meals[i] = getMealFromJson(mealsJSON.getJSONObject(i));
        }

        return new PickyDailyMealPlan(List.of(meals));
    }
}
