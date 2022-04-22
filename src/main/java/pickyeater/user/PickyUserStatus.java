package pickyeater.user;

import java.util.Date;

public class PickyUserStatus implements UserStatus {

    private final double weight;
    private final double bodyFat;
    private final double height;
    private final Date dateOfBirth;
    private final Sex sex;


    public PickyUserStatus(double weight, double height,double bodyFat, Date dateOfBirth, Sex sex) {
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
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public Sex getSex() {
        return sex;
    }
}
