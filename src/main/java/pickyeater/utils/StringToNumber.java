package pickyeater.utils;

public class StringToNumber {

    public float convertFloat(String s){
        if (s.contains(",")) {
            s = convertCommaToDot(s);
        }
        float ris = 0;
        try {
            ris = Float.parseFloat(s);
        } catch (NumberFormatException exception){
            ris = 0;
        }
        return ris;
    }

    public float convertPositiveFloat(String s){
        if (s.contains(",")) {
            s = convertCommaToDot(s);
        }
        float ris = 0;
        try {
            ris = Float.parseFloat(s);
            if (ris < 0){
                ris = 0;
            }
        } catch (NumberFormatException exception){
            ris = 0;
        }
        return ris;
    }

    public double convertDouble(String s){
        if (s.contains(",")) {
            s = convertCommaToDot(s);
        }
        double ris = 0;
        try {
            ris = Double.parseDouble(s);
        } catch (NumberFormatException exception){
            ris = 0;
        }
        return ris;
    }

    public double convertPositiveDouble(String s){
        if (s.contains(",")) {
            s = convertCommaToDot(s);
        }
        double ris = 0;
        try {
            ris = Double.parseDouble(s);
            if (ris < 0){
                ris = 0;
            }
        } catch (NumberFormatException exception){
            ris = 0;
        }
        return ris;
    }

    public int convertInteger(String s){
        int ris = 0;
        try {
            ris = Integer.parseInt(s);
        } catch (NumberFormatException exception){
            ris = 0;
        }
        return ris;
    }

    public int convertPositiveInteger(String s) {
        int ris = 0;
        try {
            ris = Integer.parseInt(s);
            if (ris < 0){
                ris = 0;
            }
        } catch (NumberFormatException exception){
            ris = 0;
        }
        return ris;
    }

    public String convertCommaToDot(String s){
        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.setCharAt(stringBuilder.indexOf(","), '.');
        return stringBuilder.toString();
    }
}
