package pickyeater.mealplan;

import java.util.Collections;
import java.util.List;

public class PickyMealPlan implements MealPlan {


    private final List<DailyMealPlan> dailyMealPlans;

    public PickyMealPlan(List<DailyMealPlan> dailyMealPlans) {
        this.dailyMealPlans = dailyMealPlans;
    }

    @Override
    public List<DailyMealPlan> getDailyMealPlans() {
        return Collections.unmodifiableList(dailyMealPlans);
    }

}
