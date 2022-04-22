//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.managers;

import database.database.IngredientsDatabase;
import database.database.MealsDatabase;
import database.database.UserDatabase;
import pickyeater.user.User;

import java.util.Optional;

public class PickyEaterManager implements EaterManager {
    private final UserDatabase userDatabase;
    private User user = null;
    private final FoodManager foodManager;

    public PickyEaterManager(UserDatabase userDatabase, MealsDatabase mealsDatabase, IngredientsDatabase ingredientsDatabase) {
        this.userDatabase = userDatabase;
        this.foodManager = new PickyFoodManager(mealsDatabase, ingredientsDatabase);
    }

    public void saveUser(User user) {
        this.userDatabase.saveUser(user);
        this.user = user;
    }

    public Optional<User> getUser() {
        if(user != null){
            return Optional.of(this.user);
        } else {
            return userDatabase.loadUser();
        }
    }

    public FoodManager getFoodManager() {
        return this.foodManager;
    }
}
