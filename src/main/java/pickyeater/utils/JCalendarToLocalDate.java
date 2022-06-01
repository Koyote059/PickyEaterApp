package pickyeater.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JCalendarToLocalDate {
    public LocalDate jCalToLocDate(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NullPointerException e) {
            return LocalDate.now();
        }
    }
}
