//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.builders;

import pickyeater.food.Meal;
import pickyeater.mealplan.DailyMealPlan;

import java.util.List;

public interface DailyMealPlanBuilder {
    void setMeal(int index, Meal meal);

    void addMeal(Meal meal);

    List<Meal> getMeals();

    DailyMealPlan build();

    void setMeals(int var1);
}
