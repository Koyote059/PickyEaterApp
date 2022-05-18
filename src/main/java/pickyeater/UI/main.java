package pickyeater.UI;

/**
 * @author Claudio Di Maio
 */
import pickyeater.UI.app.mealplanpage.MealPlanGeneratorPage;
import pickyeater.UI.choosers.MealsChooser;
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.UI.registerpage.Register1;
import pickyeater.database.*;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;

import javax.swing.*;

public class main {
    public static void main(String[] args) {

        //PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("PickyEatersDB.sqlite");
        PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("dbDiProva.sqlite");

        EaterManager eaterManager = new PickyEaterManager(
                pickyEatersDB.getUserDatabase(),
                pickyEatersDB.getIngredientsDatabase(),
                pickyEatersDB.getMealsDatabase(),pickyEatersDB.getGroceriesDatabase());

        ExecutorProvider.setEaterManager(eaterManager);

        if (eaterManager.getUserManager().getUser().isEmpty()) {  // User Database is empty
            new Register1();
        } else {  // go to the app
            new MainButton(PanelButtons.PROGRESS);
        }
    }
}
