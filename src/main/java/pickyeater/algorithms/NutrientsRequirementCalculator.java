package pickyeater.algorithms;

import pickyeater.basics.food.Nutrients;
import pickyeater.basics.user.LifeStyle;
import pickyeater.basics.user.Sex;

public interface NutrientsRequirementCalculator {
    Nutrients calculate(int height, float weight, Sex sex, LifeStyle lifeStyle);
}
