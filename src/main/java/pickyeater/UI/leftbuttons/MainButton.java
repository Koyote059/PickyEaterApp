package pickyeater.UI.leftbuttons;

import pickyeater.UI.app.dailyprogresspage.DailyProgressPage;
import pickyeater.UI.app.foodpage.FoodPage;
import pickyeater.UI.app.groceriespage.GroceriesPage;
import pickyeater.UI.app.mealplanpage.MealPlanPage;
import pickyeater.UI.app.settingspage.SettingsPage;
import pickyeater.UI.app.userpage.UserPage;
import pickyeater.database.PickyEatersDatabase;

public class MainButton {
        public MainButton(PickyEatersDatabase databases, PanelButtons panelButton) {
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
