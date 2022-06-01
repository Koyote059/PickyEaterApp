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

    /*
    public LocalDate jCalendarToLocalDate(Object object){
        StringBuilder dateTmp = new StringBuilder();
        dateTmp.append(object);
        // System.out.println(dateTmp);
        String month = dateTmp.substring(4, 7);
        String day = dateTmp.substring(8, 10);
        String year = dateTmp.substring(dateTmp.length() - 4, dateTmp.length());
        // System.out.println("month: " + month + ", day: " + day + ", year: " + year);

        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                .withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(month);
        int monthInt = accessor.get(ChronoField.MONTH_OF_YEAR);

        return LocalDate.of(parseInt(year), monthInt, parseInt(day));
    }
    */
}
