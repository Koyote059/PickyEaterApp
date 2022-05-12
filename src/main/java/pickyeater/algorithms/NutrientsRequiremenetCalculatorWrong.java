package pickyeater.algorithms;

import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.PickyNutrients;
import pickyeater.basics.user.LifeStyle;
import pickyeater.basics.user.Sex;

public class NutrientsRequiremenetCalculatorWrong implements NutrientsRequirementCalculator {
    @Override
    public Nutrients calculate(int height, float weight, Sex sex, LifeStyle lifeStyle) {
        return new PickyNutrients(10,23,53,14,5,6,7,2);
    }
}
