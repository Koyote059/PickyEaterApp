package pickyeater.basics.food;

/**
 * @author Claudio Di Maio
 */
public enum NutrientType {  // TO FIX
    PROTEIN(4), CARBOHYDRATE(4), FAT(9), ALCOHOL(7);
    final float caloriesPerGram;

    NutrientType(float caloriesPerGram) {
        this.caloriesPerGram = caloriesPerGram;
    }

    public float getCaloriesPerGram() {
        return caloriesPerGram;
    }
}
// return PROTEIN * 4 + CARBOHYDRATE * 4 + FAT * 9 + ALCOHOL * 7;