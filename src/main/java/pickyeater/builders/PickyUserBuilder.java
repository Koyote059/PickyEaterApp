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
import java.util.MissingFormatArgumentException;

public class PickyUserBuilder implements UserBuilder {
    private String name = null;
    private float weight = 0;
    private int height = 0;
    private float bodyFat = 0.0f;
    private LocalDate dateOfBirth = null;

    private Nutrients requiredNutrients = null;
    private Sex sex = null;
    private LifeStyle lifeStyle = null;
    private WeightGoal weightVariationGoal = WeightGoal.MAINTAIN_WEIGHT;
    private DailyProgresses dailyProgresses = new PickyDailyProgresses();
    private MealPlan mealPlan = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBodyFat(float bodyFat) {
        this.bodyFat = bodyFat;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setLifeStyle(LifeStyle lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public void setWeightVariationGoal(WeightGoal weightVariationGoal) {
        this.weightVariationGoal = weightVariationGoal;
    }

    public void setRequiredNutrients(Nutrients nutrients){
        this.requiredNutrients = nutrients;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    public void setDailyProgresses(Collection<Meal> meals, int burnedCalories) {
        this.dailyProgresses = new PickyDailyProgresses(burnedCalories, meals);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public float getBodyFat() {
        return bodyFat;
    }

    @Override
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public Nutrients getRequiredNutrients() {
        return requiredNutrients;
    }

    @Override
    public Sex getSex() {
        return sex;
    }

    @Override
    public LifeStyle getLifeStyle() {
        return lifeStyle;
    }

    @Override
    public WeightGoal getWeightVariationGoal() {
        return weightVariationGoal;
    }

    @Override
    public DailyProgresses getDailyProgresses() {
        return dailyProgresses;
    }

    @Override
    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public User build() {
        if (this.name == null | this.weight == 0 | this.height == 0 | this.bodyFat == 0.0 | this.dateOfBirth == null |
                this.sex == null | this.lifeStyle == null | this.requiredNutrients == null) {
            throw new MissingFormatArgumentException("Missing arguments for UserBuilder!");
        } else {
            return new PickyUser(this.name,
                    new PickyUserStatus(this.weight, this.height, this.bodyFat, this.dateOfBirth, this.sex),
                    new PickyUserGoal(this.lifeStyle, this.weightVariationGoal, this.requiredNutrients),
                    this.dailyProgresses,
                    this.mealPlan);
        }
    }

    @Override
    public String toString() {
        return "PickyUserBuilder{" + "name='" + name + '\'' + ", weight=" + weight + ", height=" + height + ", " +
                "bodyFat=" + bodyFat + ", dateOfBirth=" + dateOfBirth + ", requiredNutrients=" + requiredNutrients +
                ", sex=" + sex + ", lifeStyle=" + lifeStyle + ", weightVariationGoal=" + weightVariationGoal + ", " +
                "dailyProgresses=" + dailyProgresses + ", mealPlan=" + mealPlan + '}' + "\n";
    }
}
