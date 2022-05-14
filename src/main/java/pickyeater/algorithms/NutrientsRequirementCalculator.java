package pickyeater.algorithms;

import pickyeater.basics.food.Nutrients;
import pickyeater.basics.user.LifeStyle;
import pickyeater.basics.user.Sex;
import pickyeater.basics.user.WeightGoal;

public interface NutrientsRequirementCalculator {
    Nutrients calculate(int height, float weight, int age, Sex sex, LifeStyle lifeStyle, WeightGoal weightGoal);
}
