package pickyeater.food;

import java.util.List;

/**
 * @author Claudio Di Maio
 */

public interface Ingredient {
    Quantity getQuantity();
    String getName();
    Nutrients getNutrients();
    double getPrice();
    List<String> getTags();
}