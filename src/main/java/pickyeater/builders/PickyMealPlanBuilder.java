//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.mealplan.PickyMealPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Optional;
import java.util.stream.Collectors;

public class PickyMealPlanBuilder implements MealPlanBuilder {
    public List<DailyMealPlanBuilder> dailyMealPlanBuilders = new ArrayList<>();
    private LocalDate localDate = null;

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

    public void setBeginningDay(LocalDate localDate){
        this.localDate = localDate;
    }

    public LocalDate getBeginningDay(){
        return localDate;
    }

    public MealPlan build() {
        if(localDate == null) throw new MissingFormatArgumentException("Missing argument: localdate");
        List<DailyMealPlan> dailyMealPlans = this.dailyMealPlanBuilders.stream().map(DailyMealPlanBuilder::build).collect(Collectors.toList());
        return new PickyMealPlan(dailyMealPlans,localDate);
    }
}
