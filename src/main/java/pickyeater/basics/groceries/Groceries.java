package pickyeater.basics.groceries;

/**
 * @author Claudio Di Maio
 */
public interface Groceries {
    /**
     * Shopping price
     */
    double getPrice();
    GroceriesCheckList generateCheckList();
}
