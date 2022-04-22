import org.junit.jupiter.api.Test;
import pickyeater.food.Nutrients;
import pickyeater.food.PickyNutrients;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PickyNutrientsTest {
    Nutrients n = new PickyNutrients(1, 2, 3, 4, 5, 6, 7, 8);

    @Test
    void getProteins() {

        assertEquals(1, n.getProteins());
    }

    @Test
    void getComplexCarbs() {
        assertEquals(2, n.getComplexCarbs());
    }

    @Test
    void getSimpleCarbs() {

        assertEquals(3, n.getSimpleCarbs());
    }

    @Test
    void getFibers() {
        Nutrients n = new PickyNutrients(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(4, n.getFibers());
    }

    @Test
    void getSaturatedFats() {
        assertEquals(5, n.getSaturatedFats());
    }

    @Test
    void getUnSaturatedFats() {
        assertEquals(6, n.getUnSaturatedFats());
    }

    @Test
    void getTransFats() {
        assertEquals(7, n.getTransFats());
    }

    @Test
    void getAlcohol() {
        assertEquals(8, n.getAlcohol());
    }

    @Test
    void getCarbs() {
        Nutrients n1 = new PickyNutrients(99, 0, 1, 2, 99, 99, 99, 99);
        assertEquals(3, n1.getCarbs());
    }

    @Test
    void getFats() {
        Nutrients n2 = new PickyNutrients(99, 99, 99, 99, 3, 2, 1, 99);
        assertEquals(6, n2.getFats());
    }

    @Test
    void getCalories(){
        Nutrients biscotti = new PickyNutrients(8.5, 54.6, 20, 3.4, 0.9, 8.2, 0, 0);
        assertEquals(427.9, biscotti.getCalories());      // 433 sul pacco - OK

        Nutrients zero = new PickyNutrients(0, 0, 0, 0, 0, 0, 0, 0);
        assertEquals(0, zero.getCalories());      // 0 - OK

        Nutrients birra = new PickyNutrients(0.46, 3.55, 0, 0, 0, 0, 0, 3.9);
        assertEquals(43.34, birra.getCalories());      // 43 su google - OK
    }
}
