package pickyeater.basics.food;

import java.util.*;

public class PickyMeal implements Meal{
    private Set<Ingredient> ingredients;
    private String name;

    public PickyMeal(Set<Ingredient> ingredients, String name) {
        this.ingredients = ingredients;
        this.name = name;
    }

    @Override
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public float getWeight() {
        float weight = 0;
        for (Ingredient ingredient : ingredients) {
            weight+=ingredient.getQuantity().getAmount();
        }
        return weight;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getTags() {
        Iterator<Ingredient> ingredientIterator = this.ingredients.iterator();
        if (!ingredientIterator.hasNext()) {
            return new ArrayList<>();
        } else {
            List<String> tags = new LinkedList<>(ingredientIterator.next().getTags());

            while (ingredientIterator.hasNext()) {
                Ingredient ingredient = ingredientIterator.next();
                List<String> ingredientTags = ingredient.getTags();
                Iterator<String> tagsIterator = tags.iterator();

                while (tagsIterator.hasNext()) {
                    String tag = tagsIterator.next();
                    if (!ingredientTags.contains(tag)) {
                        tagsIterator.remove();
                    }
                }
            }

            return tags;
        }
    }

    /**
     * getNutrients: sum of all the Nutrients from the set of Ingredients.
     */
    @Override
    public Nutrients getNutrients() {
        Set<Ingredient> ingredientSet = getIngredients();
        NutrientsAccumulator accumulator = new PickyNutrientsAccumulator();
        for (Ingredient i : ingredientSet) {
            accumulator.sumNutrients(i.getNutrients());
        }
        return accumulator.generateNutrients();
    }

    @Override
    public String toString() {
        return "PickyMeal{" +
                "ingredients=" + ingredients +
                ", name='" + name + '\'' +
                '}';
    }
}