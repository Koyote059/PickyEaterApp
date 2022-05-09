package pickyeater.UI;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.UI.RegisterPage.Register1;
import pickyeater.database.*;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.PickyEaterManager;
import pickyeater.managers.PickyUserManager;
import pickyeater.managers.UserManager;

public class main {
    public static void main(String[] args) {
        UserDatabase userDatabase = new JSONUserDatabase("User_Database");
        IngredientsDatabase ingredientsDatabase = new JSONIngredientsDatabase("Ingredient_Database");
        MealsDatabase mealsDatabase = new JSONMealsDatabase("Meals_Database");

        PickyEaterManager pickyEaterManager = new PickyEaterManager(userDatabase, ingredientsDatabase, mealsDatabase);
        ExecutorProvider executorProvider = new ExecutorProvider(pickyEaterManager);

        UserManager userManager = new PickyUserManager(userDatabase);

        if (userManager.getUser().isEmpty()) {  // userDatabase is empty
            new Register1(executorProvider);
        } else {  // go to the app
            new DailyProgressPage(executorProvider);
        }
    }
}
