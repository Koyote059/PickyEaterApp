package pickyeater.managers;

import pickyeater.database.IngredientsDatabase;
import pickyeater.database.MealsDatabase;
import pickyeater.database.UserDatabase;

/**
 * @author ZiCli
 */

public class PickyEaterManager implements EaterManager {
    private FoodManager foodManager;
    private UserManager userManager;

    public PickyEaterManager(UserDatabase userDatabase, IngredientsDatabase ingredientsDatabase, MealsDatabase mealsDatabase) {
        this.foodManager = new PickyFoodManager(mealsDatabase, ingredientsDatabase);
        this.userManager = new PickyUserManager(userDatabase);
    }

    public FoodManager getFoodManager() {
        return this.foodManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

}
