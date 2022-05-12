package pickyeater.executors;

/**
 * Author: Claudio Di Maio
 */

import pickyeater.managers.EaterManager;

/**
 * Contains all executors
 */
public class ExecutorProvider {
    private EaterManager manager;

    public ExecutorProvider(EaterManager manager) {
        this.manager = manager;
    }

    public ChangeMealPlanExecutor getChangeMealPlanExecutor(){
        return new ChangeMealPlanExecutor(manager);
    }

    public CreateIngredientExecutor getCreateIngredientExecutor(){
        return new CreateIngredientExecutor(manager);
    }

    public CreateMealExecutor getCreateMealExecutor(){
        return new CreateMealExecutor(manager);
    }

    public IngredientSearcherExecutor getIngredientSearcherExecutor(){
        return new IngredientSearcherExecutor(manager);
    }

    public MealPlanViewerExecutor getMealPlanViewerExecutor(){
        return new MealPlanViewerExecutor(manager);
    }

    public MealSearcherExecutor getMealSearcherExecutor(){
        return new MealSearcherExecutor(manager);
    }

    public RegisterExecutor getRegisterExecutor(){
        return new RegisterExecutor(manager);
    }

    public UserMealsProgressesExecutor getUserMealsProgressesExecutor(){
        return new UserMealsProgressesExecutor(manager);
    }
}
