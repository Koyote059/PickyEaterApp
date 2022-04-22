package pickyeater.food;

import java.util.List;

/**
 * @author Claudio Di Maio
 */

public class PickyIngredient implements Ingredient {
    private Nutrients nutrients;
    private String name;
    private double price;
    private Quantity quantity;
    private List<String> tags;

    public PickyIngredient(Nutrients nutrients, String name, double price, Quantity quantity, List<String> tags) {
        this.nutrients = nutrients;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.tags = tags;
    }

    @Override
    public Nutrients getNutrients() {
        return nutrients;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "PickyIngredient{" +
                "nutrients=" + nutrients +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", tags=" + tags +
                '}';
    }
}