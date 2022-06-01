//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package pickyeater.builders;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.*;

import java.time.LocalDate;
import java.util.Collection;

public interface UserBuilder {
    String getName();
    void setName(String name);
    float getWeight();
    void setWeight(float weight);
    int getHeight();
    void setHeight(int height);
    float getBodyFat();
    void setBodyFat(float bodyFat);
    LocalDate getDateOfBirth();
    void setDateOfBirth(LocalDate dateOfBirth);
    Sex getSex();
    void setSex(Sex sex);
    LifeStyle getLifeStyle();
    void setLifeStyle(LifeStyle lifeStyle);
    WeightGoal getWeightVariationGoal();
    void setWeightVariationGoal(WeightGoal weightVariationGoal);
    Nutrients getRequiredNutrients();
    void setRequiredNutrients(Nutrients nutrients);
    void setDailyProgresses(Collection<Meal> eatenMeals, int burnedCalories);
    DailyProgresses getDailyProgresses();
    MealPlan getMealPlan();
    void setMealPlan(MealPlan mealPlan);
    User build();
}
