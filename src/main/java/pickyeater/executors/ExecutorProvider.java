package pickyeater.executors;

import pickyeater.executors.creators.CreateIngredientExecutor;
import pickyeater.executors.creators.CreateMealExecutor;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.executors.searcher.MealSearcherExecutor;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.executors.user.UserEditModeExecutor;
import pickyeater.executors.user.UserExecutor;
import pickyeater.managers.EaterManager;

/**
 * Contains all executors
 */
public class ExecutorProvider {
    private static EaterManager eaterManager;

    public static EaterManager getEaterManager() {
        return eaterManager;
    }

    public static void setEaterManager(EaterManager eaterManager) {
        ExecutorProvider.eaterManager = eaterManager;
    }

    public static ChangeMealPlanExecutor getChangeMealPlanExecutor() {
        return new ChangeMealPlanExecutor(eaterManager);
    }

    public static CreateIngredientExecutor getCreateIngredientExecutor() {
        return new CreateIngredientExecutor(eaterManager);
    }

    public static CreateMealExecutor getCreateMealExecutor() {
        return new CreateMealExecutor(eaterManager);
    }

    public static IngredientSearcherExecutor getIngredientSearcherExecutor() {
        return new IngredientSearcherExecutor(eaterManager);
    }

    public static MealPlanViewerExecutor getMealPlanViewerExecutor() {
        return new MealPlanViewerExecutor(eaterManager);
    }

    public static MealSearcherExecutor getMealSearcherExecutor() {
        return new MealSearcherExecutor(eaterManager);
    }

    public static RegisterExecutor getRegisterExecutor() {
        return new RegisterExecutor(eaterManager);
    }

    public static DailyProgressExecutor getDailyProgressExecutor() {
        return new DailyProgressExecutor(eaterManager);
    }

    public static UserEditModeExecutor getUserEditModeExecutor() {
        return new UserEditModeExecutor(eaterManager);
    }

    public static UserExecutor getUserExecutor() {
        return new UserExecutor(eaterManager);
    }

    public static AddBurntCaloriesExecutor getAddBurntCaloriesExecutor() {
        return new AddBurntCaloriesExecutor(eaterManager);
    }

    public static MealPlanCreatorExecutor getMealPlanExecutor() {
        return new MealPlanCreatorExecutor(eaterManager);
    }

    public static GroceriesExecutor getGroceriesExecutor() {
        return new GroceriesExecutor(eaterManager);
    }

    public static MealChooserExecutor getMealChooserExecutor() {
        return new MealChooserExecutor(eaterManager);
    }

    public static SettingsExecutor getSettingsExecutor() {
        return new SettingsExecutor(eaterManager);
    }
}
