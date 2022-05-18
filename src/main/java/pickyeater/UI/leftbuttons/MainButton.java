package pickyeater.UI.leftbuttons;

import pickyeater.UI.app.dailyprogresspage.DailyProgressPage;
import pickyeater.UI.app.foodpage.FoodPage;
import pickyeater.UI.app.groceriespage.GroceriesPage;
import pickyeater.UI.app.groceriespage.UnavailableGroceriesPage;
import pickyeater.UI.app.mealplanpage.MealPlanPage;
import pickyeater.UI.app.settingspage.SettingsPage;
import pickyeater.UI.app.userpage.UserPage;
import pickyeater.database.PickyEatersDatabase;
import pickyeater.database.SQLPickyEaterDB;

public class MainButton {

        // TODO: REMOVE
        PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("PickyEatersDB.sqlite");
        public MainButton(PanelButtons panelButton) {
                switch (panelButton) {
                        case PROGRESS -> new DailyProgressPage();
                        case DIET -> new MealPlanPage();
                        case FOOD -> new FoodPage();
                        case GROCERIES -> new UnavailableGroceriesPage();
                        case USER -> new UserPage(pickyEatersDB);
                        case SETTINGS -> new SettingsPage();
                }
        }

}
