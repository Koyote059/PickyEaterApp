package pickyeater.UI;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.UI.App.MainPages;
import pickyeater.UI.LeftButtons.MainButton;
import pickyeater.UI.LeftButtons.PanelButtons;
import pickyeater.UI.RegisterPage.Register1;
import pickyeater.database.*;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;

public class main {
    public static void main(String[] args) {
        UserDatabase userDatabase = new JSONUserDatabase("User_Database");
        IngredientsDatabase ingredientsDatabase = new JSONIngredientsDatabase("Ingredient_Database");
        MealsDatabase mealsDatabase = new JSONMealsDatabase("Meals_Database");

        Databases databases = new Databases(userDatabase, ingredientsDatabase, mealsDatabase);

        EaterManager eaterManager = new PickyEaterManager(databases.getUserDatabase(),
                databases.getIngredientsDatabase(), databases.getMealsDatabase());

        ExecutorProvider executorProvider = new ExecutorProvider(eaterManager);


        if (eaterManager.getUserManager().getUser().isEmpty()) {  // User Database is empty
            new Register1(eaterManager, executorProvider);
        } else {  // go to the app
            new MainButton(databases, PanelButtons.PROGRESS);
            // new DailyProgressPage(pickyEaterManager);
            // new MainPages(executorProvider);
        }
    }
}
