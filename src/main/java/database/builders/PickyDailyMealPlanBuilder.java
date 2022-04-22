//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.builders;

import pickyeater.food.Meal;
import pickyeater.mealplan.DailyMealPlan;
import pickyeater.mealplan.PickyDailyMealPlan;

import java.util.*;

public class PickyDailyMealPlanBuilder implements DailyMealPlanBuilder {
    private List<Meal> meals = new ArrayList();

    public void setMeal(int index, Meal meal) {
        this.meals.set(index, meal);
    }

    public void addMeal(Meal meal) {
        this.meals.add(meal);
    }

    public List<Meal> getMeals() {
        return Collections.unmodifiableList(this.meals);
    }

    public void setMeals(int mealNumber) {
        if (this.meals.size() > mealNumber) {
            this.meals = this.meals.subList(0, mealNumber);
        } else {
            List<Meal> newList = new ArrayList<>(mealNumber);
            newList.addAll(this.meals);

            for(int i = this.meals.size(); i < mealNumber; ++i) {
                newList.set(i, null);
            }

            this.meals = newList;
        }

    }

    public DailyMealPlan build() {
        Optional<Meal> nullMeal = this.meals.stream().filter(Objects::isNull).findAny();
        if (nullMeal.isPresent()) {
            throw new RuntimeException();
        } else {
            return new PickyDailyMealPlan(this.meals);
        }
    }
}
