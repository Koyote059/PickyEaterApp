package pickyeater.algorithms;

import pickyeater.basics.user.Sex;
import pickyeater.basics.user.UserStatus;

public interface BodyFatCalculator {
    float calculate(float height, float weight, Sex sex);
}
