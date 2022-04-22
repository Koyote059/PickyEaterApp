package pickyeater.food;

/**
 * @author Claudio Di Maio
 */

public interface Accumulator {
    Nutrients generateNutrients();
    void sumNutrients(Nutrients nutrients);
}