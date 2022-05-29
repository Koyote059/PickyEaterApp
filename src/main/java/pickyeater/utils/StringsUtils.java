package pickyeater.utils;

public class StringsUtils {

    public static boolean isAlpha(String string){
        for(char c : string.toCharArray()){
            if(!Character.isAlphabetic(c) && !Character.isDigit(c)) return false;
        }
        return true;
    }

    public static String capitalize(String string){
        StringBuilder stringBuilder = new StringBuilder();
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if(i==0) stringBuilder.append(Character.isUpperCase(c));
            else stringBuilder.append(Character.isLowerCase(c));
        }
        return stringBuilder.toString();
    }
}
