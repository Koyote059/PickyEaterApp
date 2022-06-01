package pickyeater.executors;

import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.builders.MealPlanBuilder;
import pickyeater.builders.PickyMealPlanBuilder;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;
import pickyeater.managers.UserManager;

import java.util.Optional;
import java.util.Set;

public class MealPlanCreatorExecutor {
    private final EaterManager eaterManager;
    private final MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();

    public MealPlanCreatorExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public MealPlanBuilder getMealPlanBuilder() {
        return mealPlanBuilder;
    }

    public User getUser() {
        UserManager userManager = eaterManager.getUserManager();
        Optional<User> userOptional = userManager.getUser();
        return userOptional.get();
    }

    public Set<Meal> getMeals() {
        FoodManager foodManager = eaterManager.getFoodManager();
        return foodManager.getMeals();
    }

    public void saveMealPlan(MealPlan mealPlan) {
        UserManager userManager = eaterManager.getUserManager();
        Optional<User> userOptional = userManager.getUser();
        User user = userOptional.get();
        user.setMealPlan(mealPlan);
        userManager.saveUser(user);
    }
}
