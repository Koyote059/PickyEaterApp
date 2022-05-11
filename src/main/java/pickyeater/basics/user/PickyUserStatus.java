package pickyeater.basics.user;

import java.time.LocalDate;
import java.util.Date;

public class PickyUserStatus implements UserStatus {

    private final float weight;
    private final float bodyFat;
    private final int height;
    private final LocalDate dateOfBirth;
    private final Sex sex;


    public PickyUserStatus(float weight, int height, float bodyFat, LocalDate dateOfBirth, Sex sex) {
        this.weight = weight;
        this.bodyFat = bodyFat;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getBodyFat() {
        return bodyFat;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public Sex getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "PickyUserStatus{" + "weight=" + weight + ", bodyFat=" + bodyFat + ", height=" + height + ", dateOfBirth=" + dateOfBirth + ", sex=" + sex + '}';
    }
}
