package pickyeater.executors;

import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class MealPlanViewerExecutor {
    private final EaterManager eaterManager;

    /**
     * Prende dal database il MealPlan (Se sta) e lo mette in MealPlanViewerExecutor
     * Se non c'è restituisce un Optional vuoto
     */
    public MealPlanViewerExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public boolean isMealPlanAvailable() {
        UserManager userManager = eaterManager.getUserManager();
        User user = userManager.getUser().get();
        return user.getMealPlan().isPresent();
    }

    public Optional<MealPlan> getMealPlan() {
        UserManager userManager = eaterManager.getUserManager();
        User user = userManager.getUser().get();
        return user.getMealPlan();
    }

    public Optional<DailyMealPlan> getDailyMealPlan(LocalDate date) {
        UserManager userManager = eaterManager.getUserManager();
        User user = userManager.getUser().get();
        MealPlan mealPlan = user.getMealPlan().get();
        LocalDate beginningDay = mealPlan.getBeginningDay();
        long daysDifference = ChronoUnit.DAYS.between(beginningDay, date);
        if (daysDifference < 0)
            return Optional.empty();
        List<DailyMealPlan> dailyMealPlans = mealPlan.getDailyMealPlans();
        int days = dailyMealPlans.size();
        return Optional.of(dailyMealPlans.get((int) (daysDifference % days)));
    }

    public void deleteMealPlan() {
        UserManager userManager = eaterManager.getUserManager();
        User user = userManager.getUser().get();
        userManager.deleteMealPlan(user);
    }

    public void addToEatenMeals(Meal meal) {
        UserManager userManager = eaterManager.getUserManager();
        User user = userManager.getUser().get();
        DailyProgresses dailyProgresses = user.getDailyProgresses();
        dailyProgresses.addEatenMeal(meal);
        userManager.saveUser(user);
    }
}
