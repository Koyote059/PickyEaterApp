package pickyeater.UI;

import pickyeater.UI.RegisterPage.Register1;

import pickyeater.UI.RegisterPage.Register2;
import pickyeater.UI.RegisterPage.Register3;
import pickyeater.database.JSONIngredientsDatabase;
import pickyeater.database.JSONMealsDatabase;
import pickyeater.database.JSONUserDatabase;
import pickyeater.executors.RegisterExecutor;
import pickyeater.managers.PickyEaterManager;

import java.awt.*;

public class main {
    public static void main(String[] args) {
        RegisterExecutor registerExecutor = new RegisterExecutor(new PickyEaterManager(new JSONUserDatabase("User_Database"), new JSONIngredientsDatabase("Ingredient_Database"), new JSONMealsDatabase("Meals_Database")));

        //new Register1(registerExecutor);
        //new Register2(registerExecutor);
        new Register3(registerExecutor);
    }
}
