package pickyeater.executors;

import pickyeater.managers.EaterManager;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;

public class SettingsExecutor {
    private final EaterManager eaterManager;
    public SettingsExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public void changeTheme(ThemesEnum themesEnum){
        new ThemeHandler().ChangeTheme(themesEnum);
    }

    public void deleteUser(){
        if (eaterManager.getUserManager().getUser().isPresent()) {
            eaterManager.getUserManager().deleteUser(eaterManager.getUserManager().getUser().get());
        }
    }

    public void resetMeals(){
        // TODO: Reset Meals
        System.out.println("MEAL RESETTED (not really)");
    }

    public void resetIngredients(){
        // TODO: Reset Ingredients
        System.out.println("INGREDIENTS RESETTED (not really)");
    }
}
