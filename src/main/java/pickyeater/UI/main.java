package pickyeater.UI;

import pickyeater.UI.RegisterPage.Register1;

import pickyeater.UI.RegisterPage.Register2;
import pickyeater.database.JSONIngredientsDatabase;
import pickyeater.database.JSONMealsDatabase;
import pickyeater.database.JSONUserDatabase;
import pickyeater.executors.RegisterExecutor;
import pickyeater.managers.PickyEaterManager;

import java.awt.*;

public class main {
    public static void main(String[] args) {
        RegisterExecutor registerExecutor = new RegisterExecutor(new PickyEaterManager(new JSONUserDatabase("User_Database"), new JSONIngredientsDatabase("Ingredient_Database"), new JSONMealsDatabase("Meals_Database")));
        //EventQueue.invokeLater(Register1::new);
        new Register1(registerExecutor);
        //EventQueue.invokeLater(Register2::new);
    }
}
