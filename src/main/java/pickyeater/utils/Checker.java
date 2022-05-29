package pickyeater.utils;

public class Checker {

    public static boolean isAlpha(String string){
        for(char c : string.toCharArray()){
            if(!Character.isAlphabetic(c) && !Character.isDigit(c)) return false;
        }
        return true;
    }
}
