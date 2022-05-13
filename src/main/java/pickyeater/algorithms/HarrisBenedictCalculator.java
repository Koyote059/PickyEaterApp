package pickyeater.algorithms;

import pickyeater.basics.food.Nutrients;
import pickyeater.basics.user.LifeStyle;
import pickyeater.basics.user.Sex;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyNutrientsBuilder;

public class HarrisBenedictCalculator implements NutrientsRequirementCalculator {
    @Override
    public Nutrients calculate(int height, float weight, int age, Sex sex, LifeStyle lifeStyle) {
        int caloriesRequirement;
        switch (sex){
            case MALE -> caloriesRequirement = (int) (655.095 + (9.563 * weight) + (1.8496 * height ) - (age * 4.6756));
            case FEMALE -> caloriesRequirement = (int) (66.473 + (13.7516 * weight ) + (5.0033 * height) - (age * 6.755));
            default -> throw new IllegalArgumentException("Illegal argument: sex -> " + sex);
        }

        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setFibers((caloriesRequirement*1.4f)/1000);
        nutrientsBuilder.setProteins((0.3f*caloriesRequirement)/4);
        nutrientsBuilder.setSaturatedFats((float)caloriesRequirement/90f);
        nutrientsBuilder.setUnSaturatedFats(((0.1f * caloriesRequirement)/9 - nutrientsBuilder.getSaturatedFats()));
        nutrientsBuilder.setSimpleCarbs((0.15f * caloriesRequirement)/4);
        nutrientsBuilder.setComplexCarbs(((0.45f * caloriesRequirement)/4) - nutrientsBuilder.getFibers());

        return nutrientsBuilder.build();
    }
}
