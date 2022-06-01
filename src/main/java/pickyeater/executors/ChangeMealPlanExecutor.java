package pickyeater.executors;

import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.builders.MealPlanBuilder;
import pickyeater.builders.PickyMealPlanBuilder;
import pickyeater.executors.searcher.MealSearcherExecutor;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

import java.util.Optional;

public class ChangeMealPlanExecutor {
    private final MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();
    private final EaterManager eaterManager;

    public ChangeMealPlanExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public MealPlanBuilder getMealPlanBuilder() {
        return this.mealPlanBuilder;
    }

    public MealSearcherExecutor getMealSearcher() {
        return new MealSearcherExecutor(this.eaterManager);
    }

    public void saveMealPlan(MealPlan mealPlan) {
        UserManager userManager = eaterManager.getUserManager();
        Optional<User> userOptional = userManager.getUser();
        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        } else {
            User user = userOptional.get();
            user.setMealPlan(mealPlan);
            userManager.saveUser(user);
        }
    }
}
