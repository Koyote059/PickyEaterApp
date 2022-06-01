package pickyeater.basics.groceries;

import pickyeater.basics.mealplan.MealPlan;

/**
 * @author Claudio Di Maio
 */
public class PickyGroceriesGenerator implements GroceriesGenerator {
    /**
     * /**
     * generate(MealPlan mealPlan): Generates the list of ingredients needed, and returns them in Groceries format
     */
    @Override
    public Groceries generate(MealPlan mealPlan) {
        PickyFinder pickyFinder = new PickyFinder();
        return new PickyGroceries(pickyFinder.getIngredients(mealPlan));
    }
}
