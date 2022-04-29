//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.LifeStyle;
import pickyeater.basics.user.Sex;
import pickyeater.basics.user.User;
import pickyeater.basics.user.WeightGoal;

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

    void setWeightVariationGoal(WeightGoal weightVariationGoal);

    void setRequiredNutrients(Nutrients nutrients);

    void setDailyProgresses(Collection<Meal> eatenMeals, int burnedCalories);

    void setMealPlan(MealPlan mealPlan);

    User build();
}
