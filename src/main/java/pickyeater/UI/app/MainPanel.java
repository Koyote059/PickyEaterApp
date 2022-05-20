package pickyeater.UI.app;

import pickyeater.UI.app.dailyprogresspage.DailyProgressPage;
import pickyeater.UI.app.foodpage.FoodPage;
import pickyeater.UI.app.groceriespage.UnavailableGroceriesPage;
import pickyeater.UI.app.mealplanpage.MealPlanPage;
import pickyeater.UI.app.settingspage.SettingsPage;
import pickyeater.UI.app.userpage.UserPage;
import pickyeater.UI.leftbuttons.PanelButtons;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JFrame {

    private final Container container;
    private static MainPanel instance;

    public MainPanel() {
        container = getContentPane();
        CardLayout layout = new CardLayout();
        container.setLayout(layout);
        DailyProgressPage progressPage = new DailyProgressPage(this);
        FoodPage foodPage = new FoodPage(this);
        UnavailableGroceriesPage unavailableGroceriesPage = new UnavailableGroceriesPage(this);
        MealPlanPage mealPlanPage = new MealPlanPage(this);
        UserPage userPage = new UserPage(this);
        SettingsPage settingsPage = new SettingsPage(this);
        add(foodPage,"FoodPage");
        add(unavailableGroceriesPage,"UnavailableGroceriesPage");
        add(mealPlanPage,"MealPlanPage");
        add(userPage,"UserPage");
        layout.show(container,"ProgressPage");
        add(settingsPage,"SettingsPage");
        setVisible(true);
        instance = this;
    }

    public static void changePage(PanelButtons panelButton){
        CardLayout layout = (CardLayout) instance.container.getLayout();
        switch (panelButton) {
            case PROGRESS -> layout.show(instance.container,"ProgressPage");
            case DIET -> layout.show(instance.container,"MealPlanPage");
            case FOOD -> layout.show(instance.container,"FoodPage");
            case GROCERIES -> layout.show(instance.container,"UnavailableGroceriesPage");
            case USER -> layout.show(instance.container,"UserPage");
            case SETTINGS -> layout.show(instance.container,"SettingsPage");
        }
    }

}
