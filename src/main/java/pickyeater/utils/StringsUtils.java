package pickyeater.utils;

public class StringsUtils {
    public static boolean isAlpha(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isAlphabetic(c) && !Character.isDigit(c) && !Character.isSpaceChar(c))
                return false;
        }
        return true;
    }

    public static String toTitle(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        StringBuilder ris = new StringBuilder();
        if (string.contains("_")) {
            ris.append(capitalize(stringBuilder.substring(0, string.indexOf("_")))).append(" ").append(capitalize(stringBuilder.substring(string.indexOf("_") + 1, string.length())));
        } else {
            ris.append(capitalize(string));
        }
        return ris.toString();
    }

    public static String capitalize(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (i == 0)
                stringBuilder.append(Character.toUpperCase(c));
            else
                stringBuilder.append(Character.toLowerCase(c));
        }
        return stringBuilder.toString();
    }
}
