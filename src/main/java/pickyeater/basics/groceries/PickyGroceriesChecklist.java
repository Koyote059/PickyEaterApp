package pickyeater.basics.groceries;

import pickyeater.basics.food.Ingredient;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Claudio Di Maio
 */

public class PickyGroceriesChecklist implements GroceriesCheckList {

    private Set<Ingredient> neededIngredients;
    private Set<Ingredient> missingIngredients;
    private Set<Ingredient> takenIngredients;

    /**
     * Constructor
     */
    public PickyGroceriesChecklist(Set<Ingredient> neededIngredients) {
        this.neededIngredients = neededIngredients;
        this.missingIngredients = new HashSet<>();
        this.takenIngredients = new HashSet<>();
    }

    public PickyGroceriesChecklist(Set<Ingredient> neededIngredients, Set<Ingredient> missingIngredients, Set<Ingredient> takenIngredients) {
        this.neededIngredients = neededIngredients;
        this.missingIngredients = missingIngredients;
        this.takenIngredients = takenIngredients;
    }

    /**
     * Getters (Unmodifiable):
     */
    @Override
    public Set<Ingredient> getNeededIngredients() {
        return Collections.unmodifiableSet(neededIngredients);
    }
    @Override
    public Set<Ingredient> getMissingIngredients() {
        return Collections.unmodifiableSet(missingIngredients);
    }
    @Override
    public Set<Ingredient> getTakenIngredients() {
        return Collections.unmodifiableSet(takenIngredients);
    }

    /**
     * Interface implementations
     */
    @Override
    public void checkIngredient(Ingredient ingredient) {
        if (neededIngredients.contains(ingredient)){
            neededIngredients.remove(ingredient);
            takenIngredients.add(ingredient);
        }
    }

    @Override
    public void unCheckIngredient(Ingredient ingredient) {
        if (takenIngredients.contains(ingredient)){
            takenIngredients.remove(ingredient);
            neededIngredients.add(ingredient);
        }
    }

    @Override
    public void setMissingIngredient(Ingredient ingredient) {
        if (neededIngredients.contains(ingredient)){
            neededIngredients.remove(ingredient);
            missingIngredients.add(ingredient);
        }
    }

    @Override
    public void unSetMissingIngredient(Ingredient ingredient) {
        if (missingIngredients.contains(ingredient)){
            missingIngredients.remove(ingredient);
            neededIngredients.add(ingredient);
        }
    }
}