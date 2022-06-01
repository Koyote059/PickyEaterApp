package pickyeater.algorithms;

import pickyeater.basics.user.Sex;

public class DeurenbergCalculator implements BodyFatCalculator {
    @Override
    public float calculate(int height, float weight, int age, Sex sex) {
        BMICalculator bmiCalculator = new StandardBMICalculator();
        float bmi = bmiCalculator.calculate(weight, height);
        float constant = sex.equals(Sex.MALE) ? 1 : 0;
        float result = ((1.2f * bmi) + (0.23f * age) - (10.8f * constant) - 5.4f);
        if (result < 60)
            return result;
        if (result <= 100)
            return result * 0.8f;
        return result * 0.55f;
    }
}
