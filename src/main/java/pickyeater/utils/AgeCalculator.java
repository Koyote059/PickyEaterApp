package pickyeater.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AgeCalculator {

    public int age(LocalDate dateOfBirth){
        if (dateOfBirth != null) {
            LocalDate localDate = LocalDate.now();
            return (int) ChronoUnit.YEARS.between(dateOfBirth, localDate);
        }
        else return 0;
    }

}
