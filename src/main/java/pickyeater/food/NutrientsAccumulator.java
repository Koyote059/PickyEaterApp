package pickyeater.food;

/**
 * @author Claudio Di Maio
 */

public interface NutrientsAccumulator {
    Nutrients generateNutrients();
    void sumNutrients(Nutrients nutrients);
}