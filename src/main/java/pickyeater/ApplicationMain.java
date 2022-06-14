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
import pickyeater.utils.Resources;

import javax.swing.*;
import java.awt.*;

public class ApplicationMain {
    public static void main(String[] args) {
        ThemeHandler.ReadTheme();
        PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB(Resources.getDatabase());
        EaterManager eaterManager = new PickyEaterManager(pickyEatersDB.getUserDatabase(), pickyEatersDB.getIngredientsDatabase(), pickyEatersDB.getMealsDatabase(), pickyEatersDB.getGroceriesDatabase());
        ExecutorProvider.setEaterManager(eaterManager);
        if (eaterManager.getUserManager().getUser().isEmpty()) {
            SwingUtilities.invokeLater(WelcomePage::new);
        } else {
            EventQueue.invokeLater(() -> {
                new MainFrame();
                MainFrame.changePage(PanelButtons.PROGRESS);
            });
        }
    }
}
