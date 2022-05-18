package pickyeater.executors;

import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class MealPlanViewerExecutor {

    private final EaterManager eaterManager;
    private final MealPlan mealPlan;

    /**
     * Prende dal database il MealPlan (Se sta) e lo mette in MealPlanViewerExecutor
     * Se non c'è restituisce un Optional vuoto
     */
    public MealPlanViewerExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        UserManager userManager = eaterManager.getUserManager();
        User user = userManager.getUser().get();
        if(user.getMealPlan().isPresent()){
            mealPlan = user.getMealPlan().get();
        } else {
            mealPlan = null;
        }
    }


    public boolean isMealPlanAvailable() {
        return mealPlan!=null;
    }

    public Optional<MealPlan> getMealPlan() {
        return Optional.ofNullable(mealPlan);
    }

    public Optional<DailyMealPlan> getDailyMealPlan(LocalDate date) {
        if(mealPlan==null) return Optional.empty();
        LocalDate beginningDay = mealPlan.getBeginningDay();
        long daysDifference = ChronoUnit.DAYS.between(beginningDay,date);
        if(daysDifference<0) return Optional.empty();
        List<DailyMealPlan> dailyMealPlans = mealPlan.getDailyMealPlans();
        int days = dailyMealPlans.size();
        return Optional.of(dailyMealPlans.get((int) (daysDifference % days)));
    }

    public void deleteMealPlan() {
        UserManager userManager = eaterManager.getUserManager();
        User user = userManager.getUser().get();
        userManager.deleteMealPlan(user);
    }
}
