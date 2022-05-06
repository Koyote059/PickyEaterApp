import org.junit.jupiter.api.Test;
import pickyeater.basics.food.*;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.PickyDailyMealPlan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PickyDailyMealPlanTest {
    Nutrients n = new PickyNutrients(1, 2, 3, 4, 5, 6, 7, 8);
    Nutrients n2 = new PickyNutrients(2, 3, 4, 5, 6, 7, 8, 9);
    List<String> listString = new ArrayList<>(List.of("Vegan"));
    Quantity q = new PickyQuantity(1, QuantityType.PIECES, 30);
    Ingredient i = new PickyIngredient(n, "Cipolla", 2.00, q, listString);
    Ingredient i2 = new PickyIngredient(n2, "Cipolla piu' grande", 3.00, q, listString);
    Set<Ingredient> setIngredient = new HashSet<>(Set.of(i, i2));

    Meal m = new PickyMeal(setIngredient, "Cibo preferito di Fede", q);
    Meal m2 = new PickyMeal(setIngredient, "Stesso cibo ma con nome diverso", q);

    List<Meal> meals = new ArrayList<>(List.of(m, m2));

    DailyMealPlan dailyMealPlan = new PickyDailyMealPlan(meals);

    @Test
    void getMeals(){
        assertEquals(meals, dailyMealPlan.getMeals());
    }
}
