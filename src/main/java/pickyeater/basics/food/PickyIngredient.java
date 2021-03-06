package pickyeater.basics.food;

import java.util.List;
import java.util.Objects;

/**
 * @author Claudio Di Maio
 */
public class PickyIngredient implements Ingredient {
    private final Nutrients nutrients;
    private final String name;
    private final float price;
    private final Quantity quantity;
    private final List<String> tags;

    public PickyIngredient(Nutrients nutrients, String name, float price, Quantity quantity, List<String> tags) {
        this.nutrients = nutrients;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.tags = tags;
    }

    @Override
    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Nutrients getNutrients() {
        return nutrients;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Ingredients are equals ONLY if they have the SAME NAME
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PickyIngredient that = (PickyIngredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "PickyIngredient{" + "nutrients=" + nutrients + ", name='" + name + '\'' + ", price=" + price + ", quantity=" + quantity + ", tags=" + tags + '}';
    }
}