package pickyeater.basics.mealplan;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PickyMealPlan implements MealPlan {


    private final List<DailyMealPlan> dailyMealPlans;
    private final LocalDate beginningDay;

    public PickyMealPlan(List<DailyMealPlan> dailyMealPlans, LocalDate beginningDay) {
        this.dailyMealPlans = dailyMealPlans;
        this.beginningDay = beginningDay;
    }

    @Override
    public List<DailyMealPlan> getDailyMealPlans() {
        return Collections.unmodifiableList(dailyMealPlans);
    }

    @Override
    public LocalDate getBeginningDay() {
        return beginningDay;
    }

    @Override
    public String toString() {
        return "PickyMealPlan{" +
                "dailyMealPlans=" + dailyMealPlans +
                ", beginningDay=" + beginningDay +
                '}';
    }
}
