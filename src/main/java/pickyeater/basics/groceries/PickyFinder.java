package pickyeater.basics.groceries;

import pickyeater.basics.food.*;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;

import java.util.*;

/**
 * @author Claudio Di Maio
 */

public class PickyFinder implements Finder {
    /**
     * Gets ingredient from MealPlan
     */

    @Override
    public Set<Ingredient> getIngredients(MealPlan mealPlan) {
        List<DailyMealPlan> dailyMealPlans = mealPlan.getDailyMealPlans();

        List<Meal> meals = new ArrayList<>();
        for (DailyMealPlan dailyMealPlan : dailyMealPlans){
            meals.addAll(dailyMealPlan.getMeals());
        }

        Set<Ingredient> ingredients = new HashSet<>();
        for (Meal meal : meals){
            ingredients = sumIngredients(ingredients, meal.getIngredients());
        }
        return ingredients;
    }

    /**
     * Sums two sets of ingredients and returns a new set with the sum of the two
     */
    @Override
    public Set<Ingredient> sumIngredients(Set<Ingredient> dst, Set<Ingredient> src) {
        Set<Ingredient> ris = new HashSet<>(dst);
        for (Ingredient ingredient : src) {
            if (dst.contains(ingredient)) {
                for (Iterator<Ingredient> dstIterator = dst.iterator(); dstIterator.hasNext(); ) {
                    Ingredient dstNext = dstIterator.next();
                    if (dstNext.equals(ingredient)) {
                        Ingredient newIngredient = sumIngredient(ingredient, dstNext);
                        ris.remove(ingredient);
                        ris.add(newIngredient);
                    }
                }
            } else {
                ris.add(ingredient);
            }
        }
        return ris;
    }

    /**
     * If two ingredients are the same (have the same name) returns the sum of the price, nutrients and quantity
     */
    @Override
    public Ingredient sumIngredient(Ingredient dst, Ingredient src) {
        Nutrients newNutrients = new PickyNutrients(dst.getNutrients().getProteins() + src.getNutrients().getProteins(),
                dst.getNutrients().getComplexCarbs() + src.getNutrients().getComplexCarbs(),
                dst.getNutrients().getSimpleCarbs() + src.getNutrients().getSimpleCarbs(),
                dst.getNutrients().getFibers() + src.getNutrients().getFibers(),
                dst.getNutrients().getSaturatedFats() + src.getNutrients().getSaturatedFats(),
                dst.getNutrients().getUnSaturatedFats() + src.getNutrients().getUnSaturatedFats(),
                dst.getNutrients().getTransFats() + src.getNutrients().getTransFats(),
                dst.getNutrients().getAlcohol() + src.getNutrients().getAlcohol());

        Quantity newQuantity = new PickyQuantity(dst.getQuantity().getAmount() + src.getQuantity().getAmount(),
                src.getQuantity().getQuantityType(),
                dst.getQuantity().getGramsPerQuantity() + src.getQuantity().getGramsPerQuantity());

        float newPrice = dst.getPrice() + src.getPrice();

        return new PickyIngredient(newNutrients, src.getName(), newPrice, newQuantity, src.getTags());
    }
}