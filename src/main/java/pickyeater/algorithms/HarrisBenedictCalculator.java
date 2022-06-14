package pickyeater.algorithms;

import pickyeater.basics.food.Nutrients;
import pickyeater.basics.user.LifeStyle;
import pickyeater.basics.user.Sex;
import pickyeater.basics.user.WeightGoal;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyNutrientsBuilder;

public class HarrisBenedictCalculator implements NutrientsRequirementCalculator {
    @Override
    public Nutrients calculate(int height, float weight, int age, Sex sex, LifeStyle lifeStyle, WeightGoal weightGoal) {
        int basalMetabolicRate;
        switch (sex) {
            case MALE:
                basalMetabolicRate = (int) (655.095 + (9.563 * weight) + (1.8496 * height) - (age * 4.6756));
                break;
            case FEMALE:
                basalMetabolicRate = (int) (66.473 + (13.7516 * weight) + (5.0033 * height) - (age * 6.755));
                break;
            default:
                throw new IllegalArgumentException("Illegal argument: sex -> " + sex);
        }
        int offset;
        switch (weightGoal) {
            case LOSE_WEIGHT:
                offset = -400;
                break;
            case MAINTAIN_WEIGHT:
                offset = 0;
                break;
            case INCREASE_WEIGHT:
                offset = 400;
                break;
            default:
                throw new IllegalArgumentException("Illegal argument: weightGoal -> " + weightGoal);
        }
        float cof;
        float proteinsPercentage;
        switch (lifeStyle) {
            case SEDENTARY:
                cof = 1.2f;
                proteinsPercentage = 0.15f;
                break;
            case LIGHTLY_ACTIVE:
                cof = 1.35f;
                proteinsPercentage = 0.18f;
                break;
            case ACTIVE:
                cof = 1.5f;
                proteinsPercentage = 0.21f;
                break;
            case VERY_ACTIVE:
                cof = 1.7f;
                proteinsPercentage = 0.23f;
                break;
            default:
                throw new IllegalArgumentException("Illegal argument: lifeStyle -> " + lifeStyle);
        }
        int caloriesRequirement = (int) (basalMetabolicRate * cof) + offset;
        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setSimpleCarbs((0.10f * caloriesRequirement) / 4);
        nutrientsBuilder.setComplexCarbs(((0.31f * caloriesRequirement) / 4) - nutrientsBuilder.getFibers());
        nutrientsBuilder.setFibers((0.14f * caloriesRequirement) / 4);
        nutrientsBuilder.setProteins((proteinsPercentage * caloriesRequirement) / 4);
        nutrientsBuilder.setSaturatedFats(((0.45f - proteinsPercentage) * (2f / 3f) * caloriesRequirement) / 9f);
        nutrientsBuilder.setUnSaturatedFats(((0.45f - proteinsPercentage) / 3 * caloriesRequirement) / 9f);
        return nutrientsBuilder.build();
    }
}
