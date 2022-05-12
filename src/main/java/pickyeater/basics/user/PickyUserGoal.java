package pickyeater.basics.user;

import pickyeater.basics.food.Nutrients;

public class PickyUserGoal implements UserGoal {

    private final LifeStyle lifeStyle;
    private final WeightGoal weightVariationGoal;

    private final Nutrients requiredNutrients;

    public PickyUserGoal(LifeStyle lifeStyle, WeightGoal weightVariationGoal, Nutrients requiredNutrients) {
        this.lifeStyle = lifeStyle;
        this.weightVariationGoal = weightVariationGoal;
        this.requiredNutrients = requiredNutrients;
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
    public Nutrients getRequiredNutrients() {
        return requiredNutrients;
    }

    @Override
    public String toString() {
        return "PickyUserGoal{" +
                "lifeStyle=" + lifeStyle +
                ", weightVariationGoal=" + weightVariationGoal +
                ", requiredNutrients=" + requiredNutrients +
                '}';
    }
}
