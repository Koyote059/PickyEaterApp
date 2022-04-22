package pickyeater.groceries;

import pickyeater.food.Ingredient;
import pickyeater.mealplan.MealPlan;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Claudio Di Maio
 */

public class PickyGroceriesGenerator implements GroceriesGenerator{

    private Set<MealPlan> mealPlans;

    /**
     * Constructor
     */
    public PickyGroceriesGenerator(Set<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    /**
     * Getter
     */
    public Set<MealPlan> getMealPlans() {
        return mealPlans;
    }

    /**
     * generate(): Generates the list of ingredients needed, and returns them in Groceries format
     */

    //ToDo: vedere caso sta lo stesso ingrediente, modificarne la quantità, prezzo, etc.
    //ToDo: Creare classe ausiliaria (Tipo Accumulator)
    @Override
    public Groceries generate() {
        //List<DailyMealPlan> dailyMealPlans;
        //List<Meal> meals;
        //Set<Ingredient> ingredients;
        Set<Ingredient> ingredientsReturn = new HashSet<>();
/*
        for (MealPlan mealPlan : mealPlans){
            dailyMealPlans = mealPlan.getDailyMealPlans();
            for (DailyMealPlan dailyMealPlan : dailyMealPlans){
                meals = dailyMealPlan.getMeals();
                for (Meal meal : meals){
                    ingredients = meal.getIngredients();
                    for (Ingredient ingredient : ingredients){
                        ingredientsReturn.add(ingredient);

                    }
                }
            }
        }
        return new PickyGroceries(ingredientsReturn);
 */
        return null;
    }
}
