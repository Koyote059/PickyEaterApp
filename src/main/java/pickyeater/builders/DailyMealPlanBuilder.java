//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;

import java.util.List;

public interface DailyMealPlanBuilder {
    void setMeal(int index, Meal meal);

    void addMeal(Meal meal);

    void removeMeal(int index);

    List<Meal> getMeals();

    DailyMealPlan build();

    void setMeals(int var1);
}
