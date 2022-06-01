package pickyeater.basics.food;

/**
 * @author Claudio Di Maio
 */
public class PickyQuantity implements Quantity {
    private final float amount;
    private final QuantityType quantityType;
    private final float gramsPerQuantity;

    public PickyQuantity(float amount, QuantityType quantityType, float gramsPerQuantity) {
        this.amount = amount;
        this.quantityType = quantityType;
        this.gramsPerQuantity = gramsPerQuantity;
    }

    public PickyQuantity(float amount) {
        this.amount = amount;
        this.quantityType = QuantityType.GRAMS;
        this.gramsPerQuantity = 1;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public QuantityType getQuantityType() {
        return quantityType;
    }

    @Override
    public float getGramsPerQuantity() {
        return gramsPerQuantity;
    }

    @Override
    public String toString() {
        return "PickyQuantity{" + "quantity=" + amount + ", quantityType=" + quantityType + ", quantityGrams=" + gramsPerQuantity + '}';
    }
}