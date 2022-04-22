package pickyeater.basics.groceries;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.mealplan.MealPlan;

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
