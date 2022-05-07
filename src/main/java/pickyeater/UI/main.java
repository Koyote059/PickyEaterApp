package pickyeater.UI;

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.database.JSONIngredientsDatabase;
import pickyeater.database.JSONMealsDatabase;
import pickyeater.database.JSONUserDatabase;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.RegisterExecutor;
import pickyeater.managers.PickyEaterManager;

import pickyeater.UI.RegisterPage.Register2;

public class main {
    public static void main(String[] args) {
        PickyEaterManager pickyEaterManager = new PickyEaterManager(new JSONUserDatabase("User_Database"), new JSONIngredientsDatabase("Ingredient_Database"), new JSONMealsDatabase("Meals_Database"));
        ExecutorProvider executorProvider = new ExecutorProvider(pickyEaterManager);


        //TODO: if (Userdatabase non è vuoto:){
        //new Register1(registerExecutor);
        new Register2(executorProvider.getRegisterExecutor());

        // TODO: } else { (Vai nell'app) }
        new DailyProgressPage(executorProvider.getUserMealsProgressesExecutor());
    }
}
