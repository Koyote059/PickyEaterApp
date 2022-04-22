//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.executors;

import pickyeater.builders.IngredientBuilder;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyIngredientBuilder;
import pickyeater.builders.PickyNutrientsBuilder;
import pickyeater.food.Ingredient;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;

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
}
