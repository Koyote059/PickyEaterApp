import org.junit.jupiter.api.Test;
import pickyeater.basics.food.NutrientType;

public class NutrientTypeTest {

    NutrientType P = NutrientType.PROTEIN;
    NutrientType C = NutrientType.CARBOHYDRATE;
    NutrientType F = NutrientType.FAT;
    NutrientType A = NutrientType.ALCOHOL;


    @Test
    void getCaloriesPerGram(){
        System.out.println("P = " + P.getCaloriesPerGram());
        System.out.println("C = " + C.getCaloriesPerGram());
        System.out.println("F = " + F.getCaloriesPerGram());
        System.out.println("A = " + A.getCaloriesPerGram());
    }
}
