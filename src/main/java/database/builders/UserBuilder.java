//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database.builders;

import pickyeater.food.Meal;
import pickyeater.mealplan.MealPlan;
import pickyeater.user.LifeStyle;
import pickyeater.user.Sex;
import pickyeater.user.User;

import java.util.Collection;
import java.util.Date;

public interface UserBuilder {
    void setName(String name);

    void setWeight(int weight);

    void setHeight(int height);

    void setBodyFat(double bodyFat);

    void setDateOfBirth(Date dateOfBirth);

    void setSex(Sex sex);

    void setLifeStyle(LifeStyle lifeStyle);

    void setWeightVariationGoal(double weightVariationGoal);

    void setDailyProgresses(Collection<Meal> eatenMeals, int burnedCalories);

    void setMealPlan(MealPlan mealPlan);

    User build();
}
