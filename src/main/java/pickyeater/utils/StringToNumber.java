package pickyeater.utils;

/**
 * Converts strings like "123.513" and "123,513" in doulbe/float/integer
 * Numbers < 0, > 10000, and invalid numbers will make the function return 0
 */
public class StringToNumber {
    public static float convertPositiveFloat(String s) {
        if (s.contains(",")) {
            s = convertCommaToDot(s);
        }
        float ris;
        try {
            ris = Float.parseFloat(s);
            if (ris < 0 || ris > 10000) {
                ris = 0;
            }
        } catch (NumberFormatException exception) {
            ris = 0;
        }
        return ris;
    }

    public static float convertPositiveFloatException(String s) throws NumberFormatException {
        if (s.contains(",")) {
            s = convertCommaToDot(s);
        }
        float ret =  Float.parseFloat(s);
        if(ret<0) throw new NumberFormatException();
        return ret;
    }

    public static int convertPositiveIntException(String s) throws NumberFormatException {
        if (s.contains(",")) {
            s = convertCommaToDot(s);
        }
        int ret =  Integer.parseInt(s);
        if(ret<=0) throw new NumberFormatException();
        return ret;
    }


    private static String convertCommaToDot(String s) {
        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.setCharAt(stringBuilder.indexOf(","), '.');
        return stringBuilder.toString();
    }

    public static double convertPositiveDouble(String s) {
        if (s.contains(",")) {
            s = convertCommaToDot(s);
        }
        double ris;
        try {
            ris = Double.parseDouble(s);
            if (ris < 0 || ris > 10000) {
                ris = 0;
            }
        } catch (NumberFormatException exception) {
            ris = 0;
        }
        return ris;
    }

    public static int convertPositiveInteger(String s) {
        int ris;
        try {
            ris = Integer.parseInt(s);
            if (ris < 0 || ris > 10000) {
                ris = 0;
            }
        } catch (NumberFormatException exception) {
            ris = 0;
        }
        return ris;
    }
}
