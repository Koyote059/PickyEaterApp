package pickyeater.basics.user;

import pickyeater.basics.mealplan.MealPlan;

import java.util.Optional;

public class PickyUser implements User {
    private final String name;
    private final UserStatus userStatus;
    private final UserGoal userGoal;
    private DailyProgresses dailyProgresses;
    private MealPlan mealPlan;

    public PickyUser(String name, UserStatus userStatus, UserGoal userGoal, DailyProgresses dailyProgresses, MealPlan mealPlan) {
        this.name = name;
        this.userStatus = userStatus;
        this.userGoal = userGoal;
        this.mealPlan = mealPlan;
        this.dailyProgresses = dailyProgresses;
    }

    public PickyUser(String name, UserStatus userStatus, UserGoal userGoal, DailyProgresses dailyProgresses) {
        this(name, userStatus, userGoal, dailyProgresses, null);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UserStatus getUserStatus() {
        return userStatus;
    }

    @Override
    public UserGoal getUserGoal() {
        return userGoal;
    }

    @Override
    public Optional<MealPlan> getMealPlan() {
        if(mealPlan == null) return Optional.empty();
        return Optional.of(mealPlan);
    }

    @Override
    public DailyProgresses getDailyProgresses() {
        return dailyProgresses;
    }

    @Override
    public void resetDailyProgresses() {
        dailyProgresses = new PickyDailyProgresses();
    }

    @Override
    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }
  
    @Override
    public String toString() {
        return "PickyUser{" +
                "name='" + name + '\'' +
                ", userStatus=" + userStatus +
                ", userGoal=" + userGoal +
                ", dailyProgresses=" + dailyProgresses +
                ", mealPlan=" + mealPlan +
                '}';
    }

}
