package pickyeater.basics.food;

/**
 * @author Claudio Di Maio
 */

public class PickyQuantity implements Quantity {
    private float weight;
    private QuantityType quantityType;
    private float gramsPerQuantity;

    public PickyQuantity(float weight, QuantityType quantityType, float gramsPerQuantity) {
        this.weight = weight;
        this.quantityType = quantityType;
        this.gramsPerQuantity = gramsPerQuantity;
    }

    public PickyQuantity(float weight){
        this.weight = weight;
        this.quantityType = QuantityType.GRAMS;
        this.gramsPerQuantity = 1;
    }

    @Override
    public float getWeight() {
        return weight;
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
        return "PickyQuantity{" + "quantity=" + weight + ", quantityType=" + quantityType + ", quantityGrams=" + gramsPerQuantity + '}';
    }
}