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
import java.util.Collections;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.stream.Collectors;

public class PickyMealPlanBuilder implements MealPlanBuilder {
    public List<DailyMealPlan> dailyMealPlans;
    private LocalDate beginningDay = LocalDate.now();

    public PickyMealPlanBuilder(){
        this.dailyMealPlans = new ArrayList<>();
    }

    public PickyMealPlanBuilder(int days){
        this.dailyMealPlans = new ArrayList<>(days);
    }

    @Override
    public void setBeginningDay(LocalDate beginningDay){
        this.beginningDay = beginningDay;
    }

    @Override
    public void addDailyMealPlan(DailyMealPlan dailyMealPlan) {
        dailyMealPlans.add(dailyMealPlan);
    }

    @Override
    public DailyMealPlan getDailyMealPlan(int day){
        return dailyMealPlans.get(day);
    }

    @Override
    public int getDays() {
        return dailyMealPlans.size();
    }

    @Override
    public void setDailyMealPlan(int day, DailyMealPlan dailyMealPlan){
        this.dailyMealPlans.add(day,dailyMealPlan);
    }

    @Override
    public void setDays(int days){
        List<DailyMealPlan> tmpDailyMealPlans = dailyMealPlans;
        dailyMealPlans = new ArrayList<>(days);

        if(days>=tmpDailyMealPlans.size()){
            dailyMealPlans.addAll(tmpDailyMealPlans);
            return;
        }
        for (int i = 0; i < days; i++) {
            dailyMealPlans.add(tmpDailyMealPlans.get(i));
        }
    }

    @Override
    public LocalDate getBeginningDay(){
        return beginningDay;
    }

    @Override
    public MealPlan build() {
        return new PickyMealPlan(dailyMealPlans, beginningDay);
    }

    @Override
    public List<DailyMealPlan> getDailyMealPlans() {
        return Collections.unmodifiableList(dailyMealPlans);
    }
}
