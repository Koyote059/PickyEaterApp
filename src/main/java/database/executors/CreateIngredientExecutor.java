//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.executors;

import database.builders.IngredientBuilder;
import database.builders.NutrientsBuilder;
import database.builders.PickyIngredientBuilder;
import database.builders.PickyNutrientsBuilder;
import pickyeater.food.Ingredient;
import database.managers.EaterManager;
import database.managers.FoodManager;

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
