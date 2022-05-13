package pickyeater.algorithms;

public class StandardBMICalculator implements BMICalculator {

    @Override
    public float calculate(float weight, int height) {
        return (float) (weight / Math.pow(height,2)*1000);
    }
}
