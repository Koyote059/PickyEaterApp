package pickyeater.basics.food;

/**
 * @author Claudio Di Maio
 */

public class PickyQuantity implements Quantity {
    private float quantity;
    private QuantityType quantityType;
    private float gramsPerQuantity;

    public PickyQuantity(float quantity, QuantityType quantityType, float gramsPerQuantity) {
        this.quantity = quantity;
        this.quantityType = quantityType;
        this.gramsPerQuantity = gramsPerQuantity;
    }

    public PickyQuantity(float quantity){
        this.quantity = quantity;
        this.quantityType = QuantityType.GRAMS;
        this.gramsPerQuantity = 1;
    }

    @Override
    public float getWeight() {
        return quantity;
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
        return "PickyQuantity{" + "quantity=" + quantity + ", quantityType=" + quantityType + ", quantityGrams=" + gramsPerQuantity + '}';
    }
}