//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.executors;

import pickyeater.builders.MealPlanBuilder;
import pickyeater.builders.PickyMealPlanBuilder;
import pickyeater.managers.EaterManager;
import pickyeater.mealplan.MealPlan;
import pickyeater.user.User;

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
        Optional<User> userOptional = this.eaterManager.getUser();
        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        } else {
            User user = (User)userOptional.get();
            user.setMealPlan(mealPlan);
            this.eaterManager.saveUser(user);
        }
    }
}
