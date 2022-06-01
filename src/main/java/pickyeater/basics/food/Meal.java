package pickyeater.basics.food;

import java.util.List;
import java.util.Set;

/**
 * @author Claudio Di Maio
 */
public interface Meal {
    String getName();
    Set<Ingredient> getIngredients();
    List<String> getTags();
    float getWeight();
    Nutrients getNutrients();
}