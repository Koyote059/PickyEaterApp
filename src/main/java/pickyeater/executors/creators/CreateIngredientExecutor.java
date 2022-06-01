package pickyeater.executors.creators;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.PickyQuantity;
import pickyeater.basics.food.QuantityType;
import pickyeater.builders.IngredientBuilder;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyIngredientBuilder;
import pickyeater.builders.PickyNutrientsBuilder;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;
import pickyeater.utils.StringToNumber;

public class CreateIngredientExecutor {
    private final EaterManager eaterManager;

    public CreateIngredientExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public IngredientBuilder getIngredientBuilder() {
        return new PickyIngredientBuilder();
    }

    public NutrientsBuilder getNutrientsBuilder() {
        return new PickyNutrientsBuilder();
    }

    public void saveIngredient(Ingredient ingredient) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        foodManager.saveIngredient(ingredient);
    }

    public IngredientBuilder createIngredient(String name, QuantityType quantityType, String grams, String price, String proteins, String carbs, String fats) {
        Nutrients nutrients = createNutrients(proteins, carbs, fats).build();
        return createIngredient(name, quantityType, grams, price, nutrients);
    }

    public NutrientsBuilder createNutrients(String proteins, String carbs, String fats) {
        float p = StringToNumber.convertPositiveFloat(proteins);
        float c = StringToNumber.convertPositiveFloat(carbs);
        float f = StringToNumber.convertPositiveFloat(fats);
        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setComplexCarbs(c);
        nutrientsBuilder.setProteins(p);
        nutrientsBuilder.setSaturatedFats(f);
        return nutrientsBuilder;
    }

    public IngredientBuilder createIngredient(String name, QuantityType quantityType, String grams, String price, Nutrients nutrients) {
        int g = StringToNumber.convertPositiveInteger(grams);
        float p = StringToNumber.convertPositiveFloat(price);
        IngredientBuilder ingredientBuilder = new PickyIngredientBuilder();
        ingredientBuilder.setName(name);
        ingredientBuilder.setQuantity(new PickyQuantity(1, quantityType, g));
        ingredientBuilder.setPrice(p);
        ingredientBuilder.setNutrients(nutrients);
        return ingredientBuilder;
    }

    public boolean existsIngredient(String name) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.hasIngredient(name);
    }

    public void deleteIngredient(Ingredient ingredient) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        foodManager.deleteIngredient(ingredient);
    }

    public boolean isIngredientUsed(Ingredient ingredient) {
        FoodManager foodManager = this.eaterManager.getFoodManager();
        return foodManager.isIngredientUsed(ingredient);
    }
}
