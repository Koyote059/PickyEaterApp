package pickyeater.UI;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.registerpage.Register1;
import pickyeater.database.*;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;
import pickyeater.themes.SystemTheme;

import java.awt.*;

public class main {
    public static void main(String[] args) {

        Color defaultGreen = Color.decode("#B1EA9D");
        Color defaultWhite = Color.decode("#FFFFFF");

        new SystemTheme().theme2();

        //PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("PickyEatersDB.sqlite");
        PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("dbDiProva.sqlite");

        EaterManager eaterManager = new PickyEaterManager(
                pickyEatersDB.getUserDatabase(),
                pickyEatersDB.getIngredientsDatabase(),
                pickyEatersDB.getMealsDatabase(),pickyEatersDB.getGroceriesDatabase());

        ExecutorProvider.setEaterManager(eaterManager);

        if (eaterManager.getUserManager().getUser().isEmpty()) {  // User Database is empty
            new Register1();
        } else {  // Go to the app
            new MainFrame();
        }


    }
}
