package pickyeater.user;

import pickyeater.food.Accumulator;
import pickyeater.food.Meal;
import pickyeater.food.Nutrients;
import pickyeater.food.PickyAccumulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PickyDailyProgresses implements DailyProgresses {

    private int burnedCalories = 0;
    private final List<Meal> eatenMeals = new ArrayList<>();

    public  PickyDailyProgresses(){}
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

    @Override
    public List<Meal> getEatenMeals(){
        return Collections.unmodifiableList(eatenMeals);
    }

    @Override
    public void addBurnedCalories(int calories) {
        burnedCalories +=calories;
    }

    @Override
    public int getBurnedCalories() {
        return burnedCalories;
    }

    public Nutrients getEatenNutrients(){
        Accumulator nutrientsAccumulator = new PickyAccumulator();
        for (Meal eatenMeal : eatenMeals) {
            nutrientsAccumulator.sumNutrients(eatenMeal.getNutrients());
        }
        return nutrientsAccumulator.generateNutrients();
    }
}
