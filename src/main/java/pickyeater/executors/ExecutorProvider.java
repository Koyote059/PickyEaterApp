package pickyeater.executors;

/**
 * Author: Claudio Di Maio
 */

import pickyeater.executors.creators.CreateIngredientExecutor;
import pickyeater.executors.creators.CreateMealExecutor;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.executors.searcher.MealSearcherExecutor;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.executors.user.UserEditModeExecutor;
import pickyeater.executors.user.UserExecutor;
import pickyeater.executors.user.UserMealsProgressesExecutor;
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

    public static ChangeMealPlanExecutor getChangeMealPlanExecutor(){
        return new ChangeMealPlanExecutor(eaterManager);
    }

    public static CreateIngredientExecutor getCreateIngredientExecutor(){
        return new CreateIngredientExecutor(eaterManager);
    }

    public static CreateMealExecutor getCreateMealExecutor(){
        return new CreateMealExecutor(eaterManager);
    }

    public static IngredientSearcherExecutor getIngredientSearcherExecutor(){
        return new IngredientSearcherExecutor(eaterManager);
    }

    public static MealPlanViewerExecutor getMealPlanViewerExecutor(){
        return new MealPlanViewerExecutor(eaterManager);
    }

    public static MealSearcherExecutor getMealSearcherExecutor(){
        return new MealSearcherExecutor(eaterManager);
    }

    public static RegisterExecutor getRegisterExecutor(){
        return new RegisterExecutor(eaterManager);
    }

    public static UserMealsProgressesExecutor getUserMealsProgressesExecutor(){
        return new UserMealsProgressesExecutor(eaterManager);
    }

    public static UserExecutor getUserExecutor(){
        return new UserExecutor(eaterManager);
    }

    public static UserEditModeExecutor getUserEditModeExecutor(){
        return new UserEditModeExecutor(eaterManager);
    }

    public static DailyProgressExecutor getDailyProgressExecutor() { return new DailyProgressExecutor(eaterManager);}
}
