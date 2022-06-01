package pickyeater.basics.user;

import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.NutrientsAccumulator;
import pickyeater.basics.food.PickyNutrientsAccumulator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PickyDailyProgresses implements DailyProgresses {
    private final List<Meal> eatenMeals = new ArrayList<>();
    private final LocalDate date = LocalDate.now(); // Todo constructor with localdate
    private int burnedCalories = 0;

    public PickyDailyProgresses() {
    }

    public PickyDailyProgresses(int burnedCalories, Collection<Meal> eatenMeals) {
        this.burnedCalories = burnedCalories;
        this.eatenMeals.addAll(eatenMeals);
    }

    @Override
    public void addEatenMeal(Meal meal) {
        eatenMeals.add(meal);
    }

    @Override
    public void removeEatenMeal(Meal meal) {
        eatenMeals.remove(meal);
    }

    public Nutrients getEatenNutrients() {
        NutrientsAccumulator nutrientsAccumulator = new PickyNutrientsAccumulator();
        for (Meal eatenMeal : eatenMeals) {
            nutrientsAccumulator.sumNutrients(eatenMeal.getNutrients());
        }
        return nutrientsAccumulator.generateNutrients();
    }

    @Override
    public List<Meal> getEatenMeals() {
        return Collections.unmodifiableList(eatenMeals);
    }

    @Override
    public void addBurnedCalories(float calories) {
        burnedCalories += calories;
    }

    @Override
    public int getBurnedCalories() {
        return burnedCalories;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "PickyDailyProgresses{" + "burnedCalories=" + burnedCalories + ", eatenMeals=" + eatenMeals + '}';
    }
}
