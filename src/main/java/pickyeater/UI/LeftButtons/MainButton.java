package pickyeater.UI.LeftButtons;

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.UI.App.FoodPage.FoodPage;
import pickyeater.UI.App.GroceriesPage.GroceriesPage;
import pickyeater.UI.App.MealPlanPage.MealPlanPage;
import pickyeater.UI.App.SettingsPage.SettingsPage;
import pickyeater.UI.App.UserPage.UserPage;
import pickyeater.database.Databases;
import pickyeater.managers.PickyEaterManager;

public class MainButton {
        public MainButton(Databases databases, PanelButtons panelButton) {
                switch (panelButton) {
                        case PROGRESS -> new DailyProgressPage(databases);
                        case DIET -> new MealPlanPage(databases);
                        case FOOD -> new FoodPage(databases);
                        case GROCERIES -> new GroceriesPage(databases);
                        case USER -> new UserPage(databases);
                        case SETTINGS -> new SettingsPage(databases);
                }
        }

}
