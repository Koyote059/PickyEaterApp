package pickyeater.basics.food;

/**
 * @author Claudio Di Maio
 */

public interface Quantity {
    float getWeight();
    QuantityType getQuantityType();
    float getGramsPerQuantity();
}