import org.junit.jupiter.api.Test;
import pickyeater.basics.food.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PickyMealTest {
    Nutrients n = new PickyNutrients(1, 2, 3, 4, 5, 6, 7, 8);
    Nutrients n2 = new PickyNutrients(2, 3, 4, 5, 6, 7, 8, 9);
    List<String> listString = new ArrayList<>(List.of("Vegan"));
    Quantity q = new PickyQuantity(1, QuantityType.PIECES, 30);
    Ingredient i = new PickyIngredient(n, "Cipolla", 2.00, q, listString);
    Ingredient i2 = new PickyIngredient(n2, "Cipolla piu' grande", 3.00, q, listString);
    Set<Ingredient> setIngredient = new HashSet<>(Set.of(i, i2));

    @Test
    void getIngredients() {
        Meal m = new PickyMeal(setIngredient, "Cibo preferito di Fede", q);

        assertEquals(setIngredient, m.getIngredients());
    }

    @Test
    void getQuantity() {
        Meal m = new PickyMeal(setIngredient, "Cibo preferito di Fede", q);

        assertEquals(q, m.getQuantity());
    }

    @Test
    void getName() {
        Meal m = new PickyMeal(setIngredient, "Cibo preferito di Fede", q);

        assertEquals("Cibo preferito di Fede", m.getName());
    }

    @Test
    void getTags() {
        Meal m = new PickyMeal(setIngredient, "Cibo preferito di Fede", q);

        assertEquals(listString, m.getTags());
    }

    @Test
    void getNutrients() {
        Meal m = new PickyMeal(setIngredient, "Cibo preferito di Fede", q);

        assertEquals(new PickyNutrients(3, 5, 7, 9, 11, 13, 15, 17), m.getNutrients());
    }
}