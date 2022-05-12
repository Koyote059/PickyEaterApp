package pickyeater.executors;

import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

import java.time.LocalDate;
import java.util.Optional;

public class MealPlanViewerExecutor {

    private EaterManager eaterManager;
    private Optional<MealPlan> mealPlanOptional;

    /**
     * Prende dal database il MealPlan (Se sta) e lo mette in MealPlanViewerExecutor
     * Se non c'è restituisce un Optional vuoto
     */
    public MealPlanViewerExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        UserManager userManager = eaterManager.getUserManager();
        User user = userManager.getUser().get();
        this.mealPlanOptional = user.getMealPlan();
    }

    /**
     * to see
     */
    /*
    public Optional<DailyMealPlan> getMealPlan(LocalDate date){
        if(mealPlanOptional.isEmpty()) return Optional.empty();
        MealPlan mealPlan = mealPlanOptional.get();
        //TODO: FINISH
    }
     */

    public void next(){

    }

    public void previous(){

    }

}
