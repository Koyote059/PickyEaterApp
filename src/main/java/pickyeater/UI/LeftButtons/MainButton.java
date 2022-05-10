package pickyeater.UI.LeftButtons;

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.UI.App.FoodPage.FoodPage;
import pickyeater.UI.App.GroceriesPage.GroceriesPage;
import pickyeater.UI.App.MealPlanPage.MealPlanPage;
import pickyeater.UI.App.SettingsPage.SettingsPage;
import pickyeater.UI.App.UserPage.UserPage;
import pickyeater.managers.PickyEaterManager;

public class MainButton {
        public MainButton(PickyEaterManager pickyEaterManager, PanelButtons panelButton) {
                switch (panelButton) {
                        case PROGRESS -> new DailyProgressPage(pickyEaterManager);
                        case DIET -> new MealPlanPage(pickyEaterManager);
                        case FOOD -> new FoodPage(pickyEaterManager);
                        case GROCERIES -> new GroceriesPage(pickyEaterManager);
                        case USER -> new UserPage(pickyEaterManager);
                        case SETTINGS -> new SettingsPage(pickyEaterManager);
                }
        }

}
