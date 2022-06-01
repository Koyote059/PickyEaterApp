package pickyeater.utils;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Quantity;
import pickyeater.builders.MealBuilder;
import pickyeater.builders.PickyMealBuilder;

public class MealQuantityConverter {
    public Meal convert(Meal meal, float newWeight) {
        MealBuilder mealBuilder = new PickyMealBuilder();
        mealBuilder.setName(meal.getName());
        float ratio = newWeight / meal.getWeight();
        for (Ingredient ingredient : meal.getIngredients()) {
            IngredientQuantityConverter converter = new IngredientQuantityConverter();
            Quantity quantity = ingredient.getQuantity();
            Ingredient convertedIngredient = converter.convert(ingredient, quantity.getAmount() * ratio);
            mealBuilder.addIngredients(convertedIngredient);
        }
        return mealBuilder.build();
    }
}
