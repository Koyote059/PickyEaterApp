package pickyeater.basics.food;

import java.util.List;

/**
 * @author Claudio Di Maio
 */

public interface Ingredient {
    Quantity getQuantity();
    String getName();
    Nutrients getNutrients();
    float getPrice();
    List<String> getTags();
}