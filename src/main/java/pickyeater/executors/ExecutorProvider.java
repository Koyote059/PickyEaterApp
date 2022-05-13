package pickyeater.executors;

/**
 * Author: Claudio Di Maio
 */

import pickyeater.managers.EaterManager;

/**
 * Contains all executors
 */
public class ExecutorProvider {
    private static EaterManager eaterManager;

    public ExecutorProvider(EaterManager eaterManager) {
        ExecutorProvider.eaterManager = eaterManager;
    }

    public EaterManager getEaterManager() {
        return eaterManager;
    }

    public void setEaterManager(EaterManager eaterManager) {
        ExecutorProvider.eaterManager = eaterManager;
    }

    public ChangeMealPlanExecutor getChangeMealPlanExecutor(){
        return new ChangeMealPlanExecutor(eaterManager);
    }

    public CreateIngredientExecutor getCreateIngredientExecutor(){
        return new CreateIngredientExecutor(eaterManager);
    }

    public CreateMealExecutor getCreateMealExecutor(){
        return new CreateMealExecutor(eaterManager);
    }

    public IngredientSearcherExecutor getIngredientSearcherExecutor(){
        return new IngredientSearcherExecutor(eaterManager);
    }

    public MealPlanViewerExecutor getMealPlanViewerExecutor(){
        return new MealPlanViewerExecutor(eaterManager);
    }

    public MealSearcherExecutor getMealSearcherExecutor(){
        return new MealSearcherExecutor(eaterManager);
    }

    public RegisterExecutor getRegisterExecutor(){
        return new RegisterExecutor(eaterManager);
    }

    public UserMealsProgressesExecutor getUserMealsProgressesExecutor(){
        return new UserMealsProgressesExecutor(eaterManager);
    }
}
