package pickyeater.groceries;

import pickyeater.mealplan.MealPlan;

/**
 * @author Claudio Di Maio
 */

public interface GroceriesGenerator {
    Groceries generate(MealPlan mealPlan);
}