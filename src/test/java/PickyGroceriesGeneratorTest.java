import org.junit.jupiter.api.Test;
import pickyeater.food.*;
import pickyeater.groceries.GroceriesGenerator;
import pickyeater.groceries.PickyGroceriesGenerator;
import pickyeater.mealplan.DailyMealPlan;
import pickyeater.mealplan.MealPlan;
import pickyeater.mealplan.PickyDailyMealPlan;
import pickyeater.mealplan.PickyMealPlan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PickyGroceriesGeneratorTest {
    //-----------------------------------------------------------------\\
    /**
     * Ingredients
     */
    List<String> eggList = new ArrayList<>(List.of("Vegetariano","Proteico","Celiaci"));
    Ingredient egg = new PickyIngredient(new PickyNutrients(12.4, 0.1, 0.1, 0.0, 3.17, 3.84, 0, 0),
            "Uovo", 0.2, new PickyQuantity(1.0, QuantityType.PIECE,58), eggList);

    List<String> pastaList = new ArrayList<>(List.of("Vegetariano","Proteico","Italian"));
    Ingredient pasta = new PickyIngredient(new PickyNutrients(13.04,68.8,2.67,3.2,0.277,0.7,0,0),
            "Pasta", 0.3, new PickyQuantity(100), pastaList);

    List<String> pecorinoList = new ArrayList<>(List.of("Vegetariano","Latticino"));
    Ingredient pecorino = new PickyIngredient(new PickyNutrients(31.8, 3, 0.7, 0, 17.115, 8, 0, 0),
            "Pecorino Romano", 0.2, new PickyQuantity(1.0, QuantityType.PIECE,58), pecorinoList);

    List<String> guancialeList = new ArrayList<>();
    Ingredient guanciale = new PickyIngredient(new PickyNutrients(17.0,3,0.7,0,26.0,8,0,0),
            "Guanciale", 0, new PickyQuantity(1.0, QuantityType.PIECE,58), guancialeList);

    /**
     * DailyMealPlan
     */
    Set<Ingredient> ingredients = new HashSet<>(Set.of(egg,pecorino,pasta,guanciale));
    Meal carbonara = new PickyMeal(ingredients, "Carbonara", new PickyQuantity(1, QuantityType.PIECE, 100), new ArrayList<>());

    Set<Ingredient> ingredients2 = new HashSet<>(Set.of(egg,pecorino,pasta,guanciale));
    Meal cacioEPepe = new PickyMeal(ingredients2, "Cacio e Pepe", new PickyQuantity(1, QuantityType.PIECE, 100),
            new ArrayList<>());

    DailyMealPlan dailyMealPlan = new PickyDailyMealPlan(List.of(cacioEPepe,carbonara,cacioEPepe));
    DailyMealPlan dailyMealPlan2 = new PickyDailyMealPlan(List.of(carbonara,carbonara));

    /**
     * MealPlan
     */
    List<DailyMealPlan> dailyMealPlanList = new ArrayList<>(List.of(dailyMealPlan, dailyMealPlan2));
    MealPlan mealPlan = new PickyMealPlan(dailyMealPlanList);

    //-----------------------------------------------------------------\\

    @Test
    void generate(){
        GroceriesGenerator pickyGroceriesGenerator = new PickyGroceriesGenerator(mealPlan);
        System.out.println(pickyGroceriesGenerator.generate(mealPlan));
    }
}
