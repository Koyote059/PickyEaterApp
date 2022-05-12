package pickyeater.basics.user;

import java.time.LocalDate;

public interface UserStatus {

    float getWeight();
    float getBodyFat();
    int getHeight();
    LocalDate getDateOfBirth();
    Sex getSex();
}
