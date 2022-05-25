package pickyeater;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.UI.registerpage.WelcomePage;
import pickyeater.database.*;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;
import pickyeater.themes.SystemTheme;

public class main {
    public static void main(String[] args) {
        new SystemTheme().theme1();

        //PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("PickyEatersDB.sqlite");
        PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("dbDiProva.sqlite");

        EaterManager eaterManager = new PickyEaterManager(
                pickyEatersDB.getUserDatabase(),
                pickyEatersDB.getIngredientsDatabase(),
                pickyEatersDB.getMealsDatabase(),pickyEatersDB.getGroceriesDatabase());

        ExecutorProvider.setEaterManager(eaterManager);



        if (eaterManager.getUserManager().getUser().isEmpty()) {  // User Database is empty
            new WelcomePage();
        } else {  // Go to the app
            new MainFrame();
            MainFrame.changePage(PanelButtons.PROGRESS);
        }


    }
}
