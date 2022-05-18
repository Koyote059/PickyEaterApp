package pickyeater.executors;


import pickyeater.basics.food.Meal;
import pickyeater.basics.user.DailyProgresses;
import pickyeater.basics.user.User;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

public class AddEatenMealExecutor {

    private final EaterManager eaterManager;
    private final DailyProgresses dailyProgresses;

    public AddEatenMealExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
        this.dailyProgresses = eaterManager.getUserManager().getUser().get().getDailyProgresses();
    }

    public void addEatenMeal(Meal eatenMeal){
        UserManager userManager = eaterManager.getUserManager();
        dailyProgresses.addEatenMeal(eatenMeal);
        // TODO: FIX MASSIVE ERROR
        userManager.saveUser(userManager.getUser().get());
    }

    /*
    // TODO - FINISH and add to AddEatenMealPage
    public Meal getIn100Grams(Meal meal, int quantity){
        Set<Ingredient> ingredientSet = meal.getIngredients();
        Ingredient ingredient;
        IngredientBuilder ingredientBuilder = new PickyIngredientBuilder();

        for (int i = 0; i < ingredientSet.size(); i++) {
            ingredient = ingredientSet.iterator().next();

            ingredientBuilder.setNutrients();
        }

        return new PickyMeal(ingredientSet, meal.getName());
    }
     */
}
