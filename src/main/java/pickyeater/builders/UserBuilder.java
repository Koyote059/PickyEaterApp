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
import java.util.Date;

public interface UserBuilder {
    void setName(String name);

    String getName();
    void setWeight(float weight);
    float getWeight();
    void setHeight(int height);
    int getHeight();
    void setBodyFat(float bodyFat);
    float getBodyFat();
    void setDateOfBirth(LocalDate dateOfBirth);
    LocalDate getDateOfBirth();
    void setSex(Sex sex);
    Sex getSex();
    void setLifeStyle(LifeStyle lifeStyle);
    LifeStyle getLifeStyle();
    void setWeightVariationGoal(WeightGoal weightVariationGoal);
    WeightGoal getWeightVariationGoal();
    void setRequiredNutrients(Nutrients nutrients);
    Nutrients getRequiredNutrients();
    void setDailyProgresses(Collection<Meal> eatenMeals, int burnedCalories);
    DailyProgresses getDailyProgresses();
    void setMealPlan(MealPlan mealPlan);
    MealPlan getMealPlan();
    User build();
}
