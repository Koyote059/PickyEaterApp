package pickyeater.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JCalUtils {
    public static LocalDate jCalToLocDate(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NullPointerException e) {
            return LocalDate.now();
        }
    }
    public static boolean youngerThan(LocalDate date1, LocalDate date2){
        return date1.isBefore(date2);
    }
}
