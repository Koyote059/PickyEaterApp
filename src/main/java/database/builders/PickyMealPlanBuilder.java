//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.builders;

import pickyeater.mealplan.DailyMealPlan;
import pickyeater.mealplan.MealPlan;
import pickyeater.mealplan.PickyMealPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PickyMealPlanBuilder implements MealPlanBuilder {
    public List<DailyMealPlanBuilder> dailyMealPlanBuilders = new ArrayList<>();

    public PickyMealPlanBuilder() {
    }

    public Optional<DailyMealPlanBuilder> getDailyMealPlan(int day) {
        if(this.dailyMealPlanBuilders.size() <= day) {
            return Optional.empty();
        } else {
            return Optional.of(this.dailyMealPlanBuilders.get(day));
        }
    }

    public int getDays() {
        return this.dailyMealPlanBuilders.size();
    }

    public void setDays(int days) {
        if (this.dailyMealPlanBuilders.size() > days) {
            this.dailyMealPlanBuilders = this.dailyMealPlanBuilders.subList(0, days);
        } else {
            List<DailyMealPlanBuilder> newList = new ArrayList<>(days);
            newList.addAll(this.dailyMealPlanBuilders);

            for(int i = this.dailyMealPlanBuilders.size(); i < days; ++i) {
                newList.set(i, new PickyDailyMealPlanBuilder());
            }

            this.dailyMealPlanBuilders = newList;
        }

    }

    public MealPlan build() {
        List<DailyMealPlan> dailyMealPlans = this.dailyMealPlanBuilders.stream().map(DailyMealPlanBuilder::build).collect(Collectors.toList());
        return new PickyMealPlan(dailyMealPlans);
    }
}
