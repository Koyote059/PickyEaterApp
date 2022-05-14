package pickyeater.utils;

public class ResizeString {

    public String ReduceStringLenght(String string, int lenght){

        if (string.length() < lenght){
            return string;
        } else {
            return string.substring(0, lenght);
        }
    }
}
