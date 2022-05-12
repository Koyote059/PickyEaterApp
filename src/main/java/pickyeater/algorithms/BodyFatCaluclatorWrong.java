package pickyeater.algorithms;

import pickyeater.basics.user.Sex;

public class BodyFatCaluclatorWrong implements BodyFatCalculator {
    @Override
    public float calculate(float height, float weight, Sex sex) {
        return 10f;
    }
}
