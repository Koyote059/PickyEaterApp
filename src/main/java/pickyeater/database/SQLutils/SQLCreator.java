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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SQLCreator {
    public Optional<User> createUser(ResultSet userRS, ResultSet dailyProgressesRS, ResultSet eatenMealsRS, ResultSet mealPlanRS, ResultSet dailyMealsRS) throws SQLException {
        UserBuilder userBuilder = new PickyUserBuilder();
        if (!userRS.next())
            return Optional.empty();
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
        List<Meal> eatenMeals = getEatenMeals(eatenMealsRS);
        if (dailyProgressesRS.next()) {
            int burnedCalories = dailyProgressesRS.getInt("burnedCalories");
            long dateEpoch = dailyProgressesRS.getLong("date");
            LocalDate date = LocalDate.ofEpochDay(dateEpoch);
            if (date.isEqual(LocalDate.now()))
                userBuilder.setDailyProgresses(eatenMeals, burnedCalories);
        }
        Optional<MealPlan> optionalMealPlan = getMealPlan(mealPlanRS, dailyMealsRS);
        optionalMealPlan.ifPresent(userBuilder::setMealPlan);
        return Optional.of(userBuilder.build());
    }

    public List<Meal> getEatenMeals(ResultSet resultSet) throws SQLException {
        List<Meal> meals = new ArrayList<>();
        String previousName = null;
        int previousNumber = 0;
        MealBuilder mealBuilder = new PickyMealBuilder();
        while (resultSet.next()) {
            int mealNumber = resultSet.getInt("mealNumber");
            String mealName = resultSet.getString("mealName");
            if (previousName == null)
                previousName = mealName;
            if (previousNumber < mealNumber) {
                mealBuilder.setName(previousName);
                meals.add(mealBuilder.build());
                mealBuilder = new PickyMealBuilder();
                previousName = mealName;
                previousNumber = mealNumber;
            }
            String ingredientName = resultSet.getString("ingredientName");
            if (ingredientName != null)
                mealBuilder.addIngredients(getIngredient(resultSet));
        }
        if (previousName != null) {
            mealBuilder.setName(previousName);
            meals.add(mealBuilder.build());
        }
        return meals;
    }

    public Optional<MealPlan> getMealPlan1(ResultSet mealPlanRS, ResultSet dailyMealsRS) throws SQLException {
        MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();
        if (mealPlanRS.next()) {
            long beginningDayEpoch = mealPlanRS.getLong("beginningDay");
            mealPlanBuilder.setBeginningDay(LocalDate.ofEpochDay(beginningDayEpoch));
        } else
            return Optional.empty();
        if (!dailyMealsRS.first())
            return Optional.ofNullable(mealPlanBuilder.build());
        DailyMealPlanBuilder dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();
        MealBuilder mealBuilder = new PickyMealBuilder();
        int prevDayNumber = dailyMealsRS.getInt("dayNumber");
        String prevMealName = dailyMealsRS.getString("mealName");
        String ingredientName = dailyMealsRS.getString("ingredientName");
        if (ingredientName != null)
            mealBuilder.addIngredients(getIngredient(dailyMealsRS));
        while (dailyMealsRS.next()) {
            int dayNumber = dailyMealsRS.getInt("dayNumber");
            String mealName = dailyMealsRS.getString("mealName");
            if (dayNumber > prevDayNumber || !mealName.equals(prevMealName)){
                mealBuilder.setName(prevMealName);
                prevMealName = mealName;
                dailyMealPlanBuilder.addMeal(mealBuilder.build());
                mealBuilder = new PickyMealBuilder();
                if (dayNumber > prevDayNumber) {
                    mealPlanBuilder.addDailyMealPlan(dailyMealPlanBuilder.build());
                    dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();
                    prevDayNumber = dayNumber;
                }
                String tmpIngredientName = dailyMealsRS.getString("ingredientName");
                if (tmpIngredientName != null) {
                    Ingredient ingredient = getIngredient(dailyMealsRS);
                    mealBuilder.addIngredients(ingredient);
                }
            }
        }
        mealPlanBuilder.addDailyMealPlan(dailyMealPlanBuilder.build());
        return Optional.of(mealPlanBuilder.build());
    }

    public Optional<MealPlan> getMealPlan(ResultSet mealPlanRS, ResultSet dailyMealsRS) throws SQLException {
        MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();
        if (mealPlanRS.next()) {
            long beginningDayEpoch = mealPlanRS.getLong("beginningDay");
            mealPlanBuilder.setBeginningDay(LocalDate.ofEpochDay(beginningDayEpoch));
        } else return Optional.empty();
        if (!dailyMealsRS.next())
            return Optional.ofNullable(mealPlanBuilder.build());

        DailyMealPlanBuilder dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();
        int previousDay = -1;
        int previousMealNumber = -1;
        String previousMealName = null;
        MealBuilder mealBuilder = new PickyMealBuilder();
        while (dailyMealsRS.next()) {
            int dayNumber = dailyMealsRS.getInt("dayNumber");
            int mealNumber = dailyMealsRS.getInt("mealNumber");
            String mealName = dailyMealsRS.getString("mealName");
            String ingredientName = dailyMealsRS.getString("ingredientName");
            Ingredient ingredient = null;
            if(ingredientName!=null) ingredient = getIngredient(dailyMealsRS);
            if(previousDay==-1){
                previousDay = dayNumber;
                previousMealNumber = mealNumber;
                previousMealName = mealName;
            }

            // Se è cambiato il giorno
            if(previousDay!=dayNumber){
                mealBuilder.setName(previousMealName);
                dailyMealPlanBuilder.addMeal(mealBuilder.build());
                mealPlanBuilder.addDailyMealPlan(dailyMealPlanBuilder.build());
                dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();
                mealBuilder = new PickyMealBuilder();
                previousDay = dayNumber;
                previousMealNumber = mealNumber;
                previousMealName = mealName;
            }

            // Se è cambiato il pasto
            if(previousMealNumber!=mealNumber){
                mealBuilder.setName(previousMealName);
                dailyMealPlanBuilder.addMeal(mealBuilder.build());
                mealBuilder = new PickyMealBuilder();
                previousMealNumber = mealNumber;
                previousMealName = mealName;
            }

            if(ingredientName!=null) mealBuilder.addIngredients(ingredient);

        }

        if(previousMealName!=null){
            mealBuilder.setName(previousMealName);
            dailyMealPlanBuilder.addMeal(mealBuilder.build());
            mealPlanBuilder.addDailyMealPlan(dailyMealPlanBuilder.build());
        }

        return Optional.ofNullable(mealPlanBuilder.build());
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
        ingredientBuilder.setQuantity(new PickyQuantity(quantity, QuantityType.valueOf(quantityType), gramsPerQuantity));
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

    public List<Ingredient> getIngredients(ResultSet resultSet) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        while (resultSet.next()) {
            ingredients.add(getIngredient(resultSet));
        }
        return ingredients;
    }

    public List<Meal> getMeals(ResultSet resultSet) throws SQLException {
        List<Meal> meals = new ArrayList<>();
        String previousName = null;
        MealBuilder mealBuilder = new PickyMealBuilder();
        while (resultSet.next()) {
            String mealName = resultSet.getString("mealName");
            if (previousName == null)
                previousName = mealName;
            if (!mealName.equals(previousName)) {
                mealBuilder.setName(previousName);
                meals.add(mealBuilder.build());
                mealBuilder = new PickyMealBuilder();
                previousName = mealName;
            }
            String ingredientName = resultSet.getString("ingredientName");
            if (ingredientName != null)
                mealBuilder.addIngredients(getIngredient(resultSet));
        }
        if (previousName != null) {
            mealBuilder.setName(previousName);
            meals.add(mealBuilder.build());
        }
        return meals;
    }

    public Groceries getGroceries(ResultSet resultSet) throws SQLException {
        Set<Ingredient> neededIngredients = new HashSet<>();
        Set<Ingredient> missingIngredients = new HashSet<>();
        Set<Ingredient> takenIngredients = new HashSet<>();
        while (resultSet.next()) {
            Ingredient ingredient = getIngredient(resultSet);
            String status = resultSet.getString("status");
            switch (status) {
                case "MISSING" -> missingIngredients.add(ingredient);
                case "NEEDED" -> neededIngredients.add(ingredient);
                case "TAKEN" -> takenIngredients.add(ingredient);
                default -> throw new SQLException("Illegal argument in GroceriesItems.status: " + status);
            }
        }
        return new PickyGroceries(neededIngredients, missingIngredients, takenIngredients);
    }
}
