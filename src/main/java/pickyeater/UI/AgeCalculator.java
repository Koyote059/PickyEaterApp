package pickyeater.UI;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AgeCalculator {

    public int Age(LocalDate dateOfBirth){
        LocalDate localDate = LocalDate.now();
        return (int)ChronoUnit.YEARS.between(dateOfBirth, localDate);
    }

}
