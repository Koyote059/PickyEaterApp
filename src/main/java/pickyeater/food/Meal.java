package pickyeater.food;

import java.util.List;
import java.util.Set;

/**
 * @author Claudio Di Maio
 */

public interface Meal {
    String getName();
    Set<Ingredient> getIngredients();
    List<String> getTags();
    Quantity getQuantity();
    Nutrients getNutrients();
}