package pickyeater.UI;

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.UI.RegisterPage.Register1;
import pickyeater.database.JSONIngredientsDatabase;
import pickyeater.database.JSONMealsDatabase;
import pickyeater.database.JSONUserDatabase;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.PickyEaterManager;

import pickyeater.UI.RegisterPage.Register2;

public class main {
    public static void main(String[] args) {
        PickyEaterManager pickyEaterManager = new PickyEaterManager(new JSONUserDatabase("User_Database"), new JSONIngredientsDatabase("Ingredient_Database"), new JSONMealsDatabase("Meals_Database"));
        ExecutorProvider executorProvider = new ExecutorProvider(pickyEaterManager);

        // if User_Database is empty:
        if (executorProvider.getRegisterExecutor().getUserBuilder().getName() == null) {    // ToDo: FIX THIS (it doesn't work)
            new Register1(executorProvider.getRegisterExecutor());
            //new Register2(executorProvider.getRegisterExecutor());
        } else  // go to the app
        new DailyProgressPage(executorProvider.getUserMealsProgressesExecutor());
    }
}
