//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.managers;

import pickyeater.database.IngredientsDatabase;
import pickyeater.database.MealsDatabase;
import pickyeater.food.Ingredient;
import pickyeater.food.Meal;

import java.util.Optional;
import java.util.Set;

public class PickyFoodManager implements FoodManager {
    private final MealsDatabase mealsDatabase;
    private final IngredientsDatabase ingredientsDatabase;

    public PickyFoodManager(MealsDatabase mealsDatabase, IngredientsDatabase ingredientsDatabase) {
        this.mealsDatabase = mealsDatabase;
        this.ingredientsDatabase = ingredientsDatabase;
    }

    public void saveMeal(Meal meal) {
        this.mealsDatabase.saveMeal(meal);
    }

    public void saveIngredient(Ingredient ingredient) {
        this.ingredientsDatabase.saveIngredient(ingredient);
    }

    public Set<Ingredient> getIngredientsThatStartWith(String name) {
        return this.ingredientsDatabase.getIngredientsThatStartWith(name);
    }

    public Set<Ingredient> getIngredients() {
        return this.ingredientsDatabase.loadEveryIngredient();
    }

    public Optional<Meal> getMeal(String mealName) {
        return this.mealsDatabase.loadMeal(mealName);
    }

    public Optional<Ingredient> getIngredient(String ingredientName) {
        return this.ingredientsDatabase.loadIngredient(ingredientName);
    }

    public Set<Meal> getMealsThatStartsWith(String name) {
        return this.mealsDatabase.getMealsThatStartWith(name);
    }

    public Set<Meal> getMeals() {
        return this.mealsDatabase.loadEveryMeal();
    }
}
