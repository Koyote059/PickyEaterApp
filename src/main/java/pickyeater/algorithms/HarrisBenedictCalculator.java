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
        switch (sex){
            case MALE -> basalMetabolicRate = (int) (655.095 + (9.563 * weight) + (1.8496 * height ) - (age * 4.6756));
            case FEMALE -> basalMetabolicRate = (int) (66.473 + (13.7516 * weight ) + (5.0033 * height) - (age * 6.755));
            default -> throw new IllegalArgumentException("Illegal argument: sex -> " + sex);
        }

        float cof;
        switch (lifeStyle){
            case SEDENTARY -> cof = 1.2f;
            case LIGHTLY_ACTIVE -> cof = 1.35f;
            case ACTIVE -> cof = 1.5f;
            case VERY_ACTIVE -> cof = 1.7f;
            default -> throw new IllegalArgumentException("Illegal argument: lifeStyle -> " + lifeStyle);
        }

        int offset;
        switch (weightGoal){
            case LOSE_WEIGHT -> offset = -400;
            case MANTAIN_WEIGHT -> offset = 0;
            case INCREASE_WEIGHT -> offset = 400;
            default -> throw new IllegalArgumentException("Illegal argument: weightGoal -> " + weightGoal);
        }

        int caloriesRequirement = (int) (basalMetabolicRate * cof) + offset;

        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setFibers((caloriesRequirement*14.0f)/1000);
        nutrientsBuilder.setProteins((0.2f*caloriesRequirement)/4);
        nutrientsBuilder.setSaturatedFats((0.025f *caloriesRequirement)/9f);
        nutrientsBuilder.setUnSaturatedFats((0.075f *caloriesRequirement)/9f);

        nutrientsBuilder.setSimpleCarbs((0.15f * caloriesRequirement)/4);
        nutrientsBuilder.setComplexCarbs(((0.45f * caloriesRequirement)/4) - nutrientsBuilder.getFibers());

        return nutrientsBuilder.build();
    }
}
