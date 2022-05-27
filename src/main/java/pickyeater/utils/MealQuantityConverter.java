package pickyeater.utils;

import pickyeater.basics.food.*;
import pickyeater.builders.*;

public class MealQuantityConverter {

    public Meal convert(Meal meal, int newWeight){
        MealBuilder mealBuilder = new PickyMealBuilder();
        mealBuilder.setName(meal.getName());
        float weightRatio = newWeight/meal.getWeight();
        for (Ingredient ingredient : meal.getIngredients()) {
            IngredientBuilder ingredientBuilder = new PickyIngredientBuilder(ingredient);
            Quantity quantity = ingredient.getQuantity();
            ingredientBuilder.setQuantity(
                    new PickyQuantity(quantity.getAmount()*weightRatio,
                            quantity.getQuantityType(),
                            quantity.getGramsPerQuantity()));
            NutrientsQuantityConverter nutrientsConverter = new NutrientsQuantityConverter();
            Nutrients newNutrients = nutrientsConverter.convert(meal.getNutrients(), newWeight);
            ingredientBuilder.setNutrients(newNutrients);
            mealBuilder.addIngredients(ingredientBuilder.build());
        }
        return mealBuilder.build();
    }

}
