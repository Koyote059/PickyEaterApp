//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;

import java.time.LocalDate;
import java.util.List;

public interface MealPlanBuilder {
    DailyMealPlan getDailyMealPlan(int day);

    int getDays();

    void setDailyMealPlan(int day, DailyMealPlan dailyMealPlan);

    void setDays(int days);

    LocalDate getBeginningDay();

    void setBeginningDay(LocalDate toLocalDate);

    void addDailyMealPlan(DailyMealPlan build);

    MealPlan build();

    List<DailyMealPlan> getDailyMealPlans();
}
