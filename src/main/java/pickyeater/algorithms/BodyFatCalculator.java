package pickyeater.algorithms;

import pickyeater.basics.user.Sex;
import pickyeater.basics.user.UserStatus;

public interface BodyFatCalculator {
    float calculate(int height, float weight, int age, Sex sex);
}
