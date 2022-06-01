package pickyeater.managers;

import pickyeater.database.GroceriesDatabase;
import pickyeater.database.IngredientsDatabase;
import pickyeater.database.MealsDatabase;
import pickyeater.database.UserDatabase;

/**
 * @author ZiCli
 */
public class PickyEaterManager implements EaterManager {
    private final FoodManager foodManager;
    private final UserManager userManager;

    public PickyEaterManager(UserDatabase userDatabase, IngredientsDatabase ingredientsDatabase, MealsDatabase mealsDatabase, GroceriesDatabase groceriesDatabase) {
        this.foodManager = new PickyFoodManager(mealsDatabase, ingredientsDatabase);
        this.userManager = new PickyUserManager(userDatabase, groceriesDatabase);
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public FoodManager getFoodManager() {
        return this.foodManager;
    }
}
