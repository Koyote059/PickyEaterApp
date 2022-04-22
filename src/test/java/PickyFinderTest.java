import org.junit.jupiter.api.Test;
import pickyeater.basics.food.*;
import pickyeater.basics.groceries.PickyFinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PickyFinderTest {
    Nutrients n = new PickyNutrients(1, 2, 3, 4, 5, 6, 7, 8);
    Nutrients n2 = new PickyNutrients(2, 3, 4, 5, 6, 7, 8, 9);
    List<String> listString = new ArrayList<>(List.of("Vegan"));
    Quantity q = new PickyQuantity(1, QuantityType.PIECE, 30);
    Ingredient i = new PickyIngredient(n, "Cipolla", 2.00, q, listString);
    Ingredient i2 = new PickyIngredient(n2, "Cipolla piu' grande", 3.00, q, listString);
    Set<Ingredient> setIngredient = new HashSet<>(Set.of(i, i2));

    Meal m = new PickyMeal(setIngredient, "Cibo preferito di Fede", q);

    PickyFinder pickyFinder = new PickyFinder();

    @Test
    void sumIngredients() {
        Ingredient i0 = new PickyIngredient(n, "Cipolla", 2.00, q, listString);
        Ingredient i1 = new PickyIngredient(n2, "Cipolla piu' grande", 3.00, q, listString);
        Ingredient i3 = new PickyIngredient(n, "Cipolla", 3.00, q, listString);
        Ingredient i4 = new PickyIngredient(n2, "Aglio", 8.00, q, listString);

        Set<Ingredient> ingredients1 = new HashSet<>(Set.of(i0, i1));
        Set<Ingredient> ingredients2 = new HashSet<>(Set.of(i3, i4, i1));

        PickyFinder pickyFinder = new PickyFinder();

        Set<Ingredient> ris = pickyFinder.sumIngredients(ingredients1, ingredients2);

        System.out.println(ingredients1);
        System.out.println(ingredients2);
        System.out.println("---");
        System.out.println(ris);
        System.out.println("---");
    }
}
