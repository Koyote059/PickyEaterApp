package pickyeater.basics.food;

/**
 * @author Claudio Di Maio
 */

public enum NutrientType {  // TO FIX
    PROTEIN(4), CARBOHYDRATE(4), FAT(9), ALCOHOL(7);

    double caloriesPerGram;

    NutrientType(double caloriesPerGram) {
        this.caloriesPerGram = caloriesPerGram;
    }

    public double getCaloriesPerGram() {
        return caloriesPerGram;
    }
}

// return PROTEIN * 4 + CARBOHYDRATE * 4 + FAT * 9 + ALCOHOL * 7;