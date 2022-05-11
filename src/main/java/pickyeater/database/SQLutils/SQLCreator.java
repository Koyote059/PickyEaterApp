package pickyeater.database.SQLutils;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.PickyQuantity;
import pickyeater.basics.food.QuantityType;
import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.PickyGroceries;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.LifeStyle;
import pickyeater.basics.user.Sex;
import pickyeater.basics.user.User;
import pickyeater.basics.user.WeightGoal;
import pickyeater.builders.*;

import javax.sql.rowset.CachedRowSet;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SQLCreator {

    public Optional<User> createUser(ResultSet userRS, ResultSet dailyProgressesRS, ResultSet eatenMealsRS, ResultSet mealPlanRS, ResultSet dailyMealsRS) throws SQLException {
        UserBuilder userBuilder = new PickyUserBuilder();
        if(!userRS.next()) return Optional.empty();
        userBuilder.setName(userRS.getString("username"));
        userBuilder.setSex(Sex.valueOf(userRS.getString("sex")));
        userBuilder.setBodyFat(userRS.getFloat("bodyFat"));
        userBuilder.setHeight(userRS.getInt("height"));
        userBuilder.setWeight(userRS.getFloat("weight"));
        long dateMillis = userRS.getLong("dateOfBirth");
        userBuilder.setDateOfBirth(LocalDate.ofEpochDay(dateMillis));
        userBuilder.setWeightVariationGoal(WeightGoal.valueOf(userRS.getString("weightGoal")));
        userBuilder.setLifeStyle(LifeStyle.valueOf(userRS.getString("lifeStyle")));
        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setComplexCarbs(userRS.getFloat("neededCarbs"));
        nutrientsBuilder.setUnSaturatedFats(userRS.getFloat("neededFats"));
        nutrientsBuilder.setProteins(userRS.getFloat("neededProteins"));
        userBuilder.setRequiredNutrients(nutrientsBuilder.build());
        List<Meal> eatenMeals = getMeals(eatenMealsRS);
        if(dailyProgressesRS.next()){
            int burnedCalories = dailyProgressesRS.getInt("burnedCalories");
            long dateEpoch = dailyProgressesRS.getLong("date");
            LocalDate date = LocalDate.ofEpochDay(dateEpoch);
            if(date.isEqual(LocalDate.now())) userBuilder.setDailyProgresses(eatenMeals,burnedCalories);
        }
        Optional<MealPlan> optionalMealPlan = getMealPlan(mealPlanRS, dailyMealsRS);
        optionalMealPlan.ifPresent(userBuilder::setMealPlan);
        return Optional.of(userBuilder.build());
    }
    public Optional<MealPlan> getMealPlan(ResultSet mealPlanRS, ResultSet dailyMealsRS) throws SQLException {
        MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();
        if(mealPlanRS.next()){
            long beginningDayEpoch = mealPlanRS.getLong("beginningDay");
            mealPlanBuilder.setBeginningDay(LocalDate.ofEpochDay(beginningDayEpoch));
        } else return Optional.empty();

        if(!dailyMealsRS.first()) return Optional.ofNullable(mealPlanBuilder.build());

        DailyMealPlanBuilder dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();

        MealBuilder mealBuilder = new PickyMealBuilder();
        int prevDayNumber = dailyMealsRS.getInt("dayNumber");
        String prevMealName = dailyMealsRS.getString("mealName");
        mealBuilder.addIngredients(getIngredient(dailyMealsRS));

        while(dailyMealsRS.next()){
            int dayNumber = dailyMealsRS.getInt("dayNumber");
            String mealName = dailyMealsRS.getString("mealName");
            Ingredient ingredient = getIngredient(dailyMealsRS);
            if(dayNumber>prevDayNumber || ! mealName.equals(prevMealName)){
                mealBuilder.setName(prevMealName);
                prevMealName = mealName;
                dailyMealPlanBuilder.addMeal(mealBuilder.build());
                mealBuilder = new PickyMealBuilder();
                if(dayNumber>prevDayNumber){
                    mealPlanBuilder.addDailyMealPlan(dailyMealPlanBuilder.build());
                    dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();
                    prevDayNumber = dayNumber;
                }
                mealBuilder.addIngredients(ingredient);
            }
        }
        return Optional.of(mealPlanBuilder.build());
    }

    public List<Ingredient> getIngredients(ResultSet resultSet) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        while(resultSet.next()){
            ingredients.add(getIngredient(resultSet));
        }
        return ingredients;
    }

    public List<Meal> getMeals(ResultSet resultSet) throws SQLException {
        List<Meal> meals = new ArrayList<>();
        String previousName = null;
        while(resultSet.next()){
            MealBuilder mealBuilder = new PickyMealBuilder();
            String mealName = resultSet.getString("mealName");
            if(!mealName.equals(previousName)) {
                if(previousName!=null){
                    mealBuilder.setName(previousName);
                    meals.add(mealBuilder.build());
                    mealBuilder = new PickyMealBuilder();
                }
                previousName = mealName;

            }
            mealBuilder.addIngredients(getIngredient(resultSet));
        }
        return meals;
    }

    public Groceries getGroceries(ResultSet resultSet) throws SQLException {
        Set<Ingredient> neededIngredients = new HashSet<>();
        Set<Ingredient> missingIngredients = new HashSet<>();
        Set<Ingredient> takenIngredients = new HashSet<>();
        while(resultSet.next()){
            Ingredient ingredient = getIngredient(resultSet);
            String status = resultSet.getString("status");
            switch (status){
                case "MISSING":
                    missingIngredients.add(ingredient);
                    break;
                case "NEEDED":
                    neededIngredients.add(ingredient);
                    break;
                case "TAKEN":
                    takenIngredients.add(ingredient);
                    break;
                default:
                    throw new SQLException("Illegal argument in GroceriesItems.status: " + status);
            }

        }
        return new PickyGroceries(neededIngredients,missingIngredients,takenIngredients);
    }
    private Ingredient getIngredient(ResultSet resultSet) throws SQLException {
        String ingredientName = resultSet.getString("ingredientName");
        float quantity = resultSet.getFloat("quantity");
        String quantityType = resultSet.getString("quantityType");
        float gramsPerQuantity = resultSet.getFloat("gramsPerQuantity");
        float price = resultSet.getFloat("price");
        float complexCarbs = resultSet.getFloat("complexCarbs");
        float simpleCarbs = resultSet.getFloat("simpleCarbs");
        float fibers = resultSet.getFloat("fibers");
        float saturatedFats = resultSet.getFloat("saturatedFats");
        float unSaturatedFats = resultSet.getFloat("unSaturatedFats");
        float transFats = resultSet.getFloat("transFats");
        float proteins = resultSet.getFloat("proteins");
        float alcohol = resultSet.getFloat("alcohol");

        IngredientBuilder ingredientBuilder = new PickyIngredientBuilder();
        ingredientBuilder.setName(ingredientName);
        ingredientBuilder.setPrice(price);
        ingredientBuilder.setQuantity(new PickyQuantity(quantity, QuantityType.valueOf(quantityType),gramsPerQuantity));
        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setComplexCarbs(complexCarbs);
        nutrientsBuilder.setSimpleCarbs(simpleCarbs);
        nutrientsBuilder.setFibers(fibers);
        nutrientsBuilder.setSaturatedFats(saturatedFats);
        nutrientsBuilder.setUnSaturatedFats(unSaturatedFats);
        nutrientsBuilder.setTransFats(transFats);
        nutrientsBuilder.setProteins(proteins);
        nutrientsBuilder.setAlcohol(alcohol);
        ingredientBuilder.setNutrients(nutrientsBuilder.build());
        return ingredientBuilder.build();
    }

}
