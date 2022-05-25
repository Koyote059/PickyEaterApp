package pickyeater.UI.app;

import pickyeater.UI.app.dailyprogresspage.DailyProgressPage;
import pickyeater.UI.app.groceriespage.UnavailableGroceriesPage;
import pickyeater.UI.app.mealplanpage.MealPlanPage;
import pickyeater.UI.app.settingspage.SettingsPage;
import pickyeater.UI.app.userpage.UserPage;
import pickyeater.UI.leftbuttons.PanelButtons;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {

    private final Container container;
    private static MainFrame instance;

    private static final Map<String,PickyPage> pages = new HashMap<>();


    public MainFrame() {
        container = getContentPane();
        CardLayout layout = new CardLayout();
        container.setLayout(layout);
        pages.put(DailyProgressPage.class.getName(),new DailyProgressPage(this));
        pages.put(UnavailableGroceriesPage.class.getName(),new UnavailableGroceriesPage(this));
        pages.put(MealPlanPage.class.getName(),new MealPlanPage(this));
        pages.put(UserPage.class.getName(),new UserPage(this));
        pages.put(SettingsPage.class.getName(),new SettingsPage(this));
        //setLocation(350,150);
        changePage(PanelButtons.PROGRESS);
        setVisible(true);
        instance = this;

        setSize(new Dimension(677,507));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void changePage(PanelButtons panelButton){
        switch (panelButton) {
            case PROGRESS -> pages.get(DailyProgressPage.class.getName()).showPage();
            case DIET -> pages.get(MealPlanPage.class.getName()).showPage();
            case GROCERIES -> pages.get(UnavailableGroceriesPage.class.getName()).showPage();
            case USER -> pages.get(UserPage.class.getName()).showPage();
            case SETTINGS -> pages.get(SettingsPage.class.getName()).showPage();
            default -> System.out.println("ERROR: " + panelButton);
        }
    }
}
