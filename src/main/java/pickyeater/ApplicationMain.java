package pickyeater;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.leftbuttons.PanelButtons;
import pickyeater.UI.pages.registerpage.WelcomePage;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.database.PickyEatersDatabase;
import pickyeater.database.SQLPickyEaterDB;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;

public class ApplicationMain {
    public static void main(String[] args) {
        ThemeHandler.ReadTheme();
        //PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("DatabasePickEater.sqlite");
        PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("dbDiProva.sqlite");
        EaterManager eaterManager = new PickyEaterManager(pickyEatersDB.getUserDatabase(), pickyEatersDB.getIngredientsDatabase(), pickyEatersDB.getMealsDatabase(), pickyEatersDB.getGroceriesDatabase());
        ExecutorProvider.setEaterManager(eaterManager);
        if (eaterManager.getUserManager().getUser().isEmpty()) {  // User Database is empty
            new WelcomePage();
        } else {  // Go to the app
            new MainFrame();
            MainFrame.changePage(PanelButtons.PROGRESS);
        }
    }
}
