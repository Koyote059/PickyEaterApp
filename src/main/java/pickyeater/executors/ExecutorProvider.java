package pickyeater.executors;

/**
 * Author: Claudio Di Maio
 */

import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.executors.searcher.MealSearcherExecutor;
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

    public static SearchMealExecutor getSearchMealExecutor(){
        return new SearchMealExecutor(eaterManager);
    }
}
