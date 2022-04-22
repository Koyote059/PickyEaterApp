package pickyeater.basics.user;

public class PickyUserGoal implements UserGoal {

    private final LifeStyle lifeStyle;
    private final double weightVariationGoal;

    public PickyUserGoal(LifeStyle lifeStyle, double weightVariationGoal) {
        this.lifeStyle = lifeStyle;
        this.weightVariationGoal = weightVariationGoal;
    }

    @Override
    public LifeStyle getLifeStyle() {
        return lifeStyle;
    }

    @Override
    public double getWeightVariationGoal() {
        return weightVariationGoal;
    }
}
