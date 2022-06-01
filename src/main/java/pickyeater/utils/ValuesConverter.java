package pickyeater.utils;

import pickyeater.basics.food.QuantityType;

import java.text.DecimalFormat;

public class ValuesConverter {
    public static String convertQuantityTypeValue(QuantityType quantityType) {
        switch (quantityType) {
            case GRAMS -> {
                return "g";
            }
            case MILLILITERS -> {
                return "ml";
            }
            case PIECES -> {
                return "pz";
            }
            default -> throw new IllegalStateException("Unexpected value: " + quantityType);
        }
    }

    public static String convertFloat(float f) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        return decimalFormat.format(f);
    }
}
