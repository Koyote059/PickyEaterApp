package pickyeater.user;

import pickyeater.food.Meal;
import pickyeater.mealplan.DailyMealPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PickyMealPlanChecklist implements MealPlanChecklist {

    private final List<Meal> mealsToEat;
    private final List<Meal> eatenMeals = new ArrayList<>();

    public PickyMealPlanChecklist(DailyMealPlan dailyMealPlan){
        mealsToEat = dailyMealPlan.getMeals();
    }

    @Override
    public void addEatenMeal(Meal meal) {
        if(!mealsToEat.contains(meal)) return;
        mealsToEat.remove(meal);
        eatenMeals.add(meal);
    }

    @Override
    public void removeEatenMeal(Meal meal){
        if(!eatenMeals.contains(meal)) return;
        eatenMeals.remove(meal);
        mealsToEat.add(meal);
    }

    @Override
    public void removeMealToEat(Meal meal) {
        mealsToEat.remove(meal);
        eatenMeals.remove(meal);
    }

    @Override
    public List<Meal> getEatenMeals(){
        return Collections.unmodifiableList(eatenMeals);
    }

    @Override
    public List<Meal> getMealsToEat(){
        return Collections.unmodifiableList(mealsToEat);
    }

    @Override
    public String toString() {
        return "PickyMealPlanChecklist{" +
                "mealsToEat=" + mealsToEat +
                ", eatenMeals=" + eatenMeals +
                '}';
    }
}
