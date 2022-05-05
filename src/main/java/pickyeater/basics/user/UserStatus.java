package pickyeater.basics.user;

import java.time.LocalDate;
import java.util.Date;

public interface UserStatus {

    double getWeight();
    double getBodyFat();
    double getHeight();
    LocalDate getDateOfBirth();
    Sex getSex();
}
