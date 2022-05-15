package pickyeater.basics.groceries;

import pickyeater.basics.mealplan.MealPlan;

/**
 * @author Claudio Di Maio
 */

public class PickyGroceriesGenerator implements GroceriesGenerator{

    private final MealPlan mealPlans;

    /**
     * Constructor
     */
    public PickyGroceriesGenerator(MealPlan mealPlans) {
        this.mealPlans = mealPlans;
    }

    /**
     * Getter
     */
    public MealPlan getMealPlans() {
        return mealPlans;
    }

    /**
     * generate(MealPlan): Generates the list of ingredients needed, and returns them in Groceries format
     */
    @Override
    public Groceries generate(MealPlan mealPlan) {
        PickyFinder pickyFinder = new PickyFinder();
        return new PickyGroceries(pickyFinder.getIngredients(mealPlan));
    }
}
