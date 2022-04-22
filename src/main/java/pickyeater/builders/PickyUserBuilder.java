//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.food.Meal;
import pickyeater.mealplan.MealPlan;
import pickyeater.user.*;

import java.util.Collection;
import java.util.Date;
import java.util.MissingFormatArgumentException;

public class PickyUserBuilder implements UserBuilder {
    private String name = null;
    private int weight = 0;
    private int height = 0;
    private double bodyFat = 0.0;
    private Date dateOfBirth = null;
    private Sex sex = null;
    private LifeStyle lifeStyle = null;
    private double weightVariationGoal = 0.0;
    private DailyProgresses dailyProgresses = new PickyDailyProgresses();
    private MealPlan mealPlan = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBodyFat(double bodyFat) {
        this.bodyFat = bodyFat;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setLifeStyle(LifeStyle lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public void setWeightVariationGoal(double weightVariationGoal) {
        this.weightVariationGoal = weightVariationGoal;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    public void setDailyProgresses(Collection<Meal> meals, int burnedCalories) {
        this.dailyProgresses = new PickyDailyProgresses(burnedCalories, meals);
    }

    public User build() {
        if (this.name == null | this.weight == 0 | this.height == 0 | this.bodyFat == 0.0 | this.dateOfBirth == null | this.sex == null | this.lifeStyle == null) {
            throw new MissingFormatArgumentException("Missing arguments for UserBuilder!");
        } else {
            return new PickyUser(this.name, new PickyUserStatus((double)this.weight, (double)this.height, this.bodyFat, this.dateOfBirth, this.sex), new PickyUserGoal(this.lifeStyle, this.weightVariationGoal), this.dailyProgresses, this.mealPlan);
        }
    }
}
