package pickyeater.database.SQLutils;

public class DBChecker {
    public static boolean isWordIllegal(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isAlphabetic(c) && !Character.isDigit(c) && !Character.isSpaceChar(c))
                return true;
        }
        return false;
    }
}
