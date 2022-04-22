package pickyeater.food;

/**
 * @author Claudio Di Maio
 */

public class PickyQuantity implements Quantity {
    private double quantity;
    private QuantityType quantityType;
    private double quantityGrams;

    public PickyQuantity(double quantity, QuantityType quantityType, double quantityGrams) {
        this.quantity = quantity;
        this.quantityType = quantityType;
        this.quantityGrams = quantityGrams;
    }

    public PickyQuantity(double quantity){
        this.quantity = quantity;
        this.quantityType = QuantityType.GRAMS;
        this.quantityGrams = 1;
    }

    @Override
    public double getQuantity() {
        return quantity;
    }

    @Override
    public QuantityType getQuantityType() {
        return quantityType;
    }

    @Override
    public double getQuantityGrams() {
        return quantityGrams;
    }

    @Override
    public String toString() {
        return "PickyQuantity{" +
                "quantity=" + quantity +
                ", quantityType=" + quantityType +
                ", quantityGrams=" + quantityGrams +
                '}';
    }
}