package pickyeater.algorithms;

public class StandardBMICalculator implements BMICalculator {
    @Override
    public float calculate(float weight, int height) {
        return (weight * 10000) / (height * height);
    }
}
