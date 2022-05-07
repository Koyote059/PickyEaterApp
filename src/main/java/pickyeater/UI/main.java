package pickyeater.UI;

import pickyeater.UI.RegisterPage.Register1;
import pickyeater.database.JSONIngredientsDatabase;
import pickyeater.database.JSONMealsDatabase;
import pickyeater.database.JSONUserDatabase;
import pickyeater.executors.RegisterExecutor;
import pickyeater.managers.PickyEaterManager;

import pickyeater.UI.RegisterPage.Register2;

public class main {
    public static void main(String[] args) {
        RegisterExecutor registerExecutor = new RegisterExecutor(new PickyEaterManager(new JSONUserDatabase("User_Database"), new JSONIngredientsDatabase("Ingredient_Database"), new JSONMealsDatabase("Meals_Database")));

        //TODO: if (Userdatabase non è vuoto:){
        //new Register1(registerExecutor);
        new Register2(registerExecutor);

        // TODO: } else { (Vai nell'app) }
    }
}
