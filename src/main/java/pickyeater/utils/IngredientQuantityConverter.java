package pickyeater.utils;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.PickyQuantity;
import pickyeater.basics.food.Quantity;
import pickyeater.builders.IngredientBuilder;
import pickyeater.builders.PickyIngredientBuilder;

public class IngredientQuantityConverter {

    public Ingredient convert(Ingredient ingredient, float newQuantity) {
        IngredientBuilder ingredientBuilder = new PickyIngredientBuilder();
        Quantity quantity = ingredient.getQuantity();
        float ratio = newQuantity/quantity.getAmount();
        ingredientBuilder.setQuantity(new PickyQuantity(
                quantity.getAmount()*ratio,
                quantity.getQuantityType(),
                quantity.getGramsPerQuantity()
        ));

        ingredientBuilder.setPrice(ingredient.getPrice() * ratio);
        ingredientBuilder.setName(ingredient.getName());
        String tags[] = new String[ingredient.getTags().size()];
        for (int i = 0; i < ingredient.getTags().size(); i++) {
            tags[i] = ingredient.getTags().get(i);
        }
        ingredientBuilder.addTags(tags);
        ingredientBuilder.setName(ingredient.getName());
        NutrientsQuantityConverter nutrientsQuantityConverter = new NutrientsQuantityConverter();
        Nutrients newNutrients = nutrientsQuantityConverter.convert(ingredient.getNutrients(),ratio);
        ingredientBuilder.setNutrients(newNutrients);
        return ingredientBuilder.build();
    }
}
