package pickyeater.basics.groceries;

import pickyeater.basics.mealplan.MealPlan;

/**
 * @author Claudio Di Maio
 */
public interface GroceriesGenerator {
    Groceries generate(MealPlan mealPlan);
}