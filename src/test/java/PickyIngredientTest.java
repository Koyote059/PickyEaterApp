import org.junit.jupiter.api.Test;
import pickyeater.basics.food.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PickyIngredientTest {
    Nutrients n = new PickyNutrients(1, 2, 3, 4, 5, 6, 7, 8);
    List<String> listString = new ArrayList<>(List.of("Vegan"));
    Quantity q = new PickyQuantity(1, QuantityType.PIECE, 30);
    Ingredient i = new PickyIngredient(n, "Cipolla", 2.00, q, listString);

    @Test
    void getNutrients() {
        assertEquals(n, i.getNutrients());
    }

    @Test
    void getName() {
        assertEquals("Cipolla", i.getName());
    }

    @Test
    void getPrice() {
        assertEquals(2.00, i.getPrice());
    }

    @Test
    void getQuantity() {
        assertEquals(q, i.getQuantity());
    }

    @Test
    void getTags() {
        assertEquals(listString, i.getTags());
    }
}
