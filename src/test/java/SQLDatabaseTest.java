import org.junit.jupiter.api.Test;
import pickyeater.algorithms.HarrisBenedictCalculator;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.food.*;
import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.basics.groceries.PickyGroceries;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.*;
import pickyeater.builders.*;
import pickyeater.database.*;
import pickyeater.utils.IngredientQuantityConverter;
import pickyeater.utils.MealQuantityConverter;

import java.time.LocalDate;
import java.util.*;

public class SQLDatabaseTest {
    SQLPickyEaterDB database = new SQLPickyEaterDB("dbDiProva.sqlite");
    UserDatabase userDatabase = database.getUserDatabase();
    MealsDatabase mealsDatabase = database.getMealsDatabase();
    IngredientsDatabase ingredientsDatabase = database.getIngredientsDatabase();
    GroceriesDatabase groceriesDatabase = database.getGroceriesDatabase();

    List<Ingredient> ingredients;
    List<Meal> meals;

    @Test
    public void nutrientsTest(){
        NutrientsRequirementCalculator calculator = new HarrisBenedictCalculator();
        Nutrients nutrients = calculator.calculate(180,77,21,Sex.MALE,LifeStyle.VERY_ACTIVE,WeightGoal.MAINTAIN_WEIGHT);
        System.out.println(nutrients);
        System.out.println(nutrients.getCalories());
    }
    @Test
    public void Test0(){
        User user = userDatabase.loadUser().get();
        MealPlan mealPlan = user.getMealPlan().get();
        List<DailyMealPlan> dailyMealPlans = mealPlan.getDailyMealPlans();
        for (int i = 0; i < dailyMealPlans.size(); i++) {
            DailyMealPlan dailyMealPlan = dailyMealPlans.get(i);
            for (Meal meal : dailyMealPlan.getMeals()) {
                System.out.println(i + ") " + meal.getName());
                for (Ingredient ingredient : meal.getIngredients()) {
                    System.out.println("    " + ingredient.getName() + " -> " + ingredient.getQuantity().getAmount() + " " + ingredient.getQuantity().getQuantityType());
                }
            }
        }
    }

    @Test
    public void test(){

        this.ingredients = getIngredients();
        this.meals = getMeals();

        ingredients.forEach(ingredientsDatabase::saveIngredient);
        meals.forEach(mealsDatabase::saveMeal);
        User     user = getUser();
        MealPlan mealPlan = user.getMealPlan().get();
        List<DailyMealPlan> dailyMealPlans = mealPlan.getDailyMealPlans();
        for (int i = 0; i < dailyMealPlans.size(); i++) {
            DailyMealPlan dailyMealPlan = dailyMealPlans.get(i);
            for (Meal meal : dailyMealPlan.getMeals()) {
                System.out.println(i + ") " + meal.getName());
                for (Ingredient ingredient : meal.getIngredients()) {
                    System.out.println("    " + ingredient.getName() + " -> " + ingredient.getQuantity().getAmount() + " " + ingredient.getQuantity().getQuantityType());
                }
            }

        }
        userDatabase.saveUser(user);
    }

    @Test
    public void ingredientQuantityConverterTests(){
        IngredientQuantityConverter converter = new IngredientQuantityConverter();
        Ingredient ingredient = getRandomIngredient("Pollo");
        System.out.println(ingredient);

        Ingredient convertedIngredient = converter.convert(ingredient,100);
        System.out.println(convertedIngredient);
    }

    @Test
    public void mealQuantityConverterTests(){
        this.ingredients = getIngredients();
        MealQuantityConverter converter = new MealQuantityConverter();
        Meal meal = getRandomMeal("Lasagna");
        System.out.println(meal);
        System.out.println(meal.getWeight());

        Meal convertedMeal = converter.convert(meal,100);
        System.out.println(convertedMeal);
        System.out.println(convertedMeal.getWeight());
    }

    @Test
    public void ingredientDatabaseTest(){
        Ingredient mela = getRandomIngredient("Mela");
        ingredientsDatabase.saveIngredient(mela);
        System.out.println(mela);

        Ingredient i = ingredientsDatabase.loadIngredient("Mela").get();
        System.out.println(i);
    }

    @Test
    public void mealDatabaseTest(){
        this.ingredients = getIngredients();
        this.meals = getMeals();

        ingredients.forEach(ingredientsDatabase::saveIngredient);

        Meal meal = meals.get(Math.abs(new Random().nextInt()%meals.size()));
        System.out.println(meal);
        System.out.println(meal.getWeight());
        mealsDatabase.saveMeal(meal);

        MealQuantityConverter converter = new MealQuantityConverter();

        Meal convertedMeal = mealsDatabase.loadMeal(meal.getName()).get();
        System.out.println(convertedMeal);
        System.out.println(convertedMeal.getWeight());
    }

    private User getUser(){
        UserBuilder userBuilder = new PickyUserBuilder();
        userBuilder.setName("Federico");
        userBuilder.setWeight(77);
        userBuilder.setHeight(180);
        userBuilder.setSex(Sex.MALE);
        userBuilder.setBodyFat(15);
        userBuilder.setDateOfBirth(LocalDate.of(2001,4,9));
        userBuilder.setLifeStyle(LifeStyle.ACTIVE);
        userBuilder.setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);
        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setComplexCarbs(200);
        nutrientsBuilder.setProteins(120);
        nutrientsBuilder.setUnSaturatedFats(40);
        userBuilder.setRequiredNutrients(nutrientsBuilder.build());
        userBuilder.setDailyProgresses(getEatenMeals(),400);
        userBuilder.setMealPlan(getMealPlan());
        return userBuilder.build();
    }

    private List<Meal> getEatenMeals() {
        List<Meal> meals = new ArrayList<>();
        Random random = new Random();
        int boundaries = 1 + random.nextInt()%(this.meals.size()/4);
        for(int i = 0; i<boundaries; i++){
            Meal meal = this.meals.get(Math.abs((random.nextInt()%this.meals.size())));
            meals.add(meal);
        }

        return meals;
    }


    private MealPlan getMealPlan(){
        MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();
        for(int j=0;j<6;j++){
            DailyMealPlanBuilder dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();
            Random random = new Random();
            int bound = random.nextInt()%6+3;
            for(int i = 0;i<bound;i++){
                Meal meal = meals.get(Math.abs(random.nextInt()%meals.size()));;
                dailyMealPlanBuilder.addMeal(meal);
            }
            mealPlanBuilder.addDailyMealPlan(dailyMealPlanBuilder.build());
        }
        return mealPlanBuilder.build();
    }


    private List<Ingredient> getIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(getRandomIngredient("carota"));
        ingredients.add(getRandomIngredient("piselli"));
        ingredients.add(getRandomIngredient("pasta"));
        ingredients.add(getRandomIngredient("pollo"));
        ingredients.add(getRandomIngredient("uovo"));
        ingredients.add(getRandomIngredient("mozzarella"));
        ingredients.add(getRandomIngredient("farina"));
        ingredients.add(getRandomIngredient("sedano"));
        ingredients.add(getRandomIngredient("pomodoro"));
        ingredients.add(getRandomIngredient("lattuga"));
        ingredients.add(getRandomIngredient("cipolla"));
        ingredients.add(getRandomIngredient("parmigiano"));
        ingredients.add(getRandomIngredient("bistecca"));
        ingredients.add(getRandomIngredient("olive"));
        ingredients.add(getRandomIngredient("mais"));
        ingredients.add(getRandomIngredient("barbabietola"));
        ingredients.add(getRandomIngredient("patate"));
        ingredients.add(getRandomIngredient("pasta sfoglia"));
        ingredients.add(getRandomIngredient("olio"));
        ingredients.add(getRandomIngredient("finocchio"));
        ingredients.add(getRandomIngredient("ravanelli"));
        ingredients.add(getRandomIngredient("peperoni"));
        ingredients.add(getRandomIngredient("cetriolo"));
        ingredients.add(getRandomIngredient("zucchina"));
        ingredients.add(getRandomIngredient("zucca"));
        return ingredients;
    }

    private Ingredient getRandomIngredient(String name) {
        IngredientBuilder ingredientBuilder = new PickyIngredientBuilder();
        ingredientBuilder.setName(name);
        Random random = new Random();
        QuantityType quantityType = null;
        float gramsPerQuantity = random.nextFloat()%100;
        switch (Math.abs(random.nextInt()%3)){
            case 0:
                quantityType = QuantityType.GRAMS;
                gramsPerQuantity = 1;
                break;
            case 1:
                quantityType = QuantityType.MILLILITERS;
                break;
            case 2:
                quantityType = QuantityType.PIECES;
                break;
        }
        ingredientBuilder.setQuantity(new PickyQuantity(random.nextFloat(),quantityType,gramsPerQuantity));
        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setComplexCarbs(random.nextFloat()%100);
        nutrientsBuilder.setSimpleCarbs(random.nextFloat()%100);
        nutrientsBuilder.setFibers(random.nextFloat()%100);
        nutrientsBuilder.setUnSaturatedFats(random.nextFloat()%100);
        nutrientsBuilder.setSaturatedFats(random.nextFloat()%100);
        nutrientsBuilder.setTransFats(random.nextFloat()%100);
        nutrientsBuilder.setProteins(random.nextFloat()%100);
        nutrientsBuilder.setAlcohol(random.nextFloat()%100);
        ingredientBuilder.setNutrients(nutrientsBuilder.build());
        return ingredientBuilder.build();
    }

    private List<Meal> getMeals(){
        List<Meal> meals = new ArrayList<>();
        meals.add(getRandomMeal("Pasta strana"));
        meals.add(getRandomMeal("Riso magico"));
        meals.add(getRandomMeal("Fettine di occhi"));
        meals.add(getRandomMeal("Biscotti fatati"));
        meals.add(getRandomMeal("Cioccolata orribile"));
        meals.add(getRandomMeal("Torta dello studente"));
        meals.add(getRandomMeal("Pasto del palestrato"));
        meals.add(getRandomMeal("Pasto fit"));
        meals.add(getRandomMeal("Ciabatta buona"));
        meals.add(getRandomMeal("Cartone pregiato"));
        meals.add(getRandomMeal("Elettronico edibile"));
        meals.add(getRandomMeal("Caranbara"));
        meals.add(getRandomMeal("Amatriciana ritoccata"));
        meals.add(getRandomMeal("Sugo vegetariano"));
        meals.add(getRandomMeal("Animale morto"));
        meals.add(getRandomMeal("Troppo speziato"));
        meals.add(getRandomMeal("Cicorie sottovuoto"));

        return meals;
    }

    private Meal getRandomMeal(String name) {
        MealBuilder mealBuilder = new PickyMealBuilder();
        mealBuilder.setName(name);
        Random random = new Random();
        int bound = Math.abs(random.nextInt()%(ingredients.size()/3))+4;
        for(int i = 0; i< bound; i++){
            Ingredient ingredient = ingredients.get(Math.abs(random.nextInt()%ingredients.size()));
            mealBuilder.addIngredients(ingredient);
        }
        return mealBuilder.build();
    }


}
