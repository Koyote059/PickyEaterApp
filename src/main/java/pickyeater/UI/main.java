package pickyeater.UI;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.UI.RegisterPage.Register1;
import pickyeater.database.JSONIngredientsDatabase;
import pickyeater.database.JSONMealsDatabase;
import pickyeater.database.JSONUserDatabase;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.PickyEaterManager;

public class main {
    public static void main(String[] args) {
        PickyEaterManager pickyEaterManager = new PickyEaterManager(new JSONUserDatabase("User_Database"), new JSONIngredientsDatabase("Ingredient_Database"), new JSONMealsDatabase("Meals_Database"));
        ExecutorProvider executorProvider = new ExecutorProvider(pickyEaterManager);

        new DailyProgressPage(executorProvider);
        /*
        // if User_Database is empty: (there is no name)
        if (executorProvider.getRegisterExecutor().getUserBuilder().getName() == null) {    //TODO: FIX THIS
            new Register1(executorProvider);
            //new Register2(executorProvider);
        } else {  // go to the app
            new DailyProgressPage(executorProvider);
        }
         */
    }
}
