package pickyeater.groceries;
import pickyeater.food.Ingredient;
import pickyeater.mealplan.MealPlan;

import java.util.Set;

/**
 * Auxiliary Class -> Used in PickyGroceriesGenerator
 * @author Claudio Di Maio
 */
public interface Finder {
    Set<Ingredient> getIngredients(MealPlan mealPlan);
    Set<Ingredient> sumIngredients(Set<Ingredient> dst, Set<Ingredient> src);
    Ingredient sumIngredient(Ingredient dst, Ingredient src);
}
