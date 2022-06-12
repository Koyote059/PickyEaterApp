package pickyeater.executors;

import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.builders.MealPlanBuilder;
import pickyeater.builders.PickyMealPlanBuilder;
import pickyeater.managers.EaterManager;
import pickyeater.managers.FoodManager;
import pickyeater.managers.UserManager;
import pickyeater.utils.MealPlanGeneratorBundle;
import pickyeater.utils.Resources;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealPlanCreatorExecutor {
    private final EaterManager eaterManager;
    private final MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();

    public MealPlanCreatorExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public MealPlanBuilder getMealPlanBuilder() {
        return mealPlanBuilder;
    }

    public User getUser() {
        UserManager userManager = eaterManager.getUserManager();
        Optional<User> userOptional = userManager.getUser();
        return userOptional.get();
    }

    public Set<Meal> getMeals() {
        FoodManager foodManager = eaterManager.getFoodManager();
        return foodManager.getMeals();
    }

    public void saveMealPlan(MealPlan mealPlan) {
        UserManager userManager = eaterManager.getUserManager();
        Optional<User> userOptional = userManager.getUser();
        User user = userOptional.get();
        user.setMealPlan(mealPlan);
        userManager.saveUser(user);
    }

    public MealPlanGeneratorBundle getBundle() {
        MealPlanGeneratorBundle bundle = new MealPlanGeneratorBundle();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Resources.getMPGeneratorSettings()));
            String line = reader.readLine();
            if(line==null) throw new IOException();
            StringTokenizer tokenizer = new StringTokenizer(line," ");
            int days = Integer.parseInt(tokenizer.nextToken());
            int mealsInADay = Integer.parseInt(tokenizer.nextToken());
            if(days>20 || mealsInADay>20 || days<=0 || mealsInADay <=0) throw new IOException();
            bundle.setDays(days);
            bundle.setMealsInADay(mealsInADay);
        } catch (IOException | NumberFormatException e) {
            try {
                Files.deleteIfExists(Path.of("res/MPGeneratorSettings"));
                Files.createFile(Path.of("res/MPGeneratorSettings"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            bundle.setDays(7);
            bundle.setMealsInADay(4);
        }

        return bundle;
    }

    public void saveBundle(MealPlanGeneratorBundle bundle) {
        try(FileWriter writer = new FileWriter("res/MPGeneratorSettings")) {
            writer.write(bundle.getDays() + " " + bundle.getMealsInADay());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
