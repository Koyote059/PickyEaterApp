package pickyeater.user;

import pickyeater.mealplan.MealPlan;

import java.util.Optional;

public interface User {
    String getName();
    UserStatus getUserStatus();
    UserGoal getUserGoal();
    Optional<MealPlan> getMealPlan();
    void setMealPlan(MealPlan mealPlan);
    DailyProgresses getDailyProgresses();
    void resetDailyProgresses();

}
