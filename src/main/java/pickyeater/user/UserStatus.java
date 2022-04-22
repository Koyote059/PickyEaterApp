package pickyeater.user;

import java.util.Date;

public interface UserStatus {

    double getWeight();
    double getBodyFat();
    double getHeight();
    Date getDateOfBirth();
    Sex getSex();
}
