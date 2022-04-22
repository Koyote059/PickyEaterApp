import org.junit.jupiter.api.Test;
import pickyeater.basics.food.PickyQuantity;
import pickyeater.basics.food.Quantity;
import pickyeater.basics.food.QuantityType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PickyQuantityTest {
    Quantity q1 = new PickyQuantity(1, QuantityType.PIECE, 30);
    Quantity q2 = new PickyQuantity(2, QuantityType.GRAMS, 20);
    Quantity q3 = new PickyQuantity(3, QuantityType.LITERS, 10);

    @Test
    void getQuantity() {
        assertEquals(1, q1.getQuantity());
        assertEquals(2, q2.getQuantity());
        assertEquals(3, q3.getQuantity());
    }

    @Test
    void getQuantityType() {
        assertEquals(QuantityType.PIECE, q1.getQuantityType());
        assertEquals(QuantityType.GRAMS, q2.getQuantityType());
        assertEquals(QuantityType.LITERS, q3.getQuantityType());
    }

    @Test
    void getQuantityGrams() {
        assertEquals(30, q1.getQuantityGrams());
        assertEquals(20, q2.getQuantityGrams());
        assertEquals(10, q3.getQuantityGrams());
    }
}