package pickyeater.executors;

import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.basics.groceries.GroceriesGenerator;
import pickyeater.basics.groceries.PickyGroceriesGenerator;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

import java.util.Optional;

public class GroceriesExecutor {

    private final EaterManager eaterManager;
    private Groceries groceries = null;
    public GroceriesExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public boolean isGroceriesAvailable() {
        if(isGroceriesGenerated()) return true;
        UserManager userManager = eaterManager.getUserManager();
        Optional<User> userOptional = userManager.getUser();
        if(userOptional.isEmpty()) throw new RuntimeException("Error in database: User");
        User user = userOptional.get();
        Optional<MealPlan> mealPlanOptional = user.getMealPlan();
        if(mealPlanOptional.isEmpty()) return false;
        MealPlan mealPlan = mealPlanOptional.get();
        GroceriesGenerator groceriesGenerator = new PickyGroceriesGenerator();
        groceries = groceriesGenerator.generate(mealPlan);
        return true;

    }

    public Optional<Groceries> getGroceries() {
        if(groceries==null) return Optional.empty();
        return Optional.of(groceries);
    }

    public void saveGroceries(GroceriesCheckList groceriesCheckList) {
        UserManager userManager = eaterManager.getUserManager();
        userManager.saveGroceries(groceriesCheckList);
    }

    public boolean isGroceriesGenerated() {
        UserManager userManager = eaterManager.getUserManager();
        if(groceries!=null) return true;
        Optional<Groceries> groceriesOptional = userManager.getGroceries();
        if(groceriesOptional.isEmpty()){
            return false;
        }
        groceries = groceriesOptional.get();
        return true;
    }

    public void deleteGroceries() {
        UserManager userManager = eaterManager.getUserManager();
        userManager.deleteGroceries(groceries);
    }
}
