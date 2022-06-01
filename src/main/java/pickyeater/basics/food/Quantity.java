package pickyeater.basics.food;

/**
 * @author Claudio Di Maio
 */
public interface Quantity {
    float getAmount();
    QuantityType getQuantityType();
    float getGramsPerQuantity();
}