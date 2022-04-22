//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.mealplan.MealPlan;

import java.util.Optional;

public interface MealPlanBuilder {
    Optional<DailyMealPlanBuilder> getDailyMealPlan(int day);

    int getDays();

    void setDays(int days);

    MealPlan build();
}
