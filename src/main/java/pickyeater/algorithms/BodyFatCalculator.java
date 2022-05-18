package pickyeater.algorithms;

import pickyeater.basics.user.Sex;

public interface BodyFatCalculator {
    float calculate(int height, float weight, int age, Sex sex);
}
