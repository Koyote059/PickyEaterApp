package pickyeater.basics.user;

import pickyeater.basics.food.Nutrients;

public interface UserGoal {
    LifeStyle getLifeStyle();
    WeightGoal getWeightVariationGoal();
    Nutrients getRequiredNutrients();
}
