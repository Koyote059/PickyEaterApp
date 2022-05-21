package pickyeater.executors;

import pickyeater.UI.app.groceriespage.GroceriesPage;
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
    public GroceriesExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public boolean isGroceriesAvailable() {
        UserManager userManager = eaterManager.getUserManager();
        Optional<Groceries> optionalGroceries = userManager.getGroceries();
        return optionalGroceries.isPresent();
    }

    public Optional<Groceries> getGroceries() {
        UserManager userManager = eaterManager.getUserManager();
        return userManager.getGroceries();
    }

    public void saveGroceries(GroceriesCheckList groceriesCheckList) {
        UserManager userManager = eaterManager.getUserManager();
        userManager.saveGroceries(groceriesCheckList);
    }

    public void deleteGroceries() {
        UserManager userManager = eaterManager.getUserManager();
        Optional<Groceries> groceriesOptional = userManager.getGroceries();
        if(groceriesOptional.isEmpty()) return;
        Groceries groceries = groceriesOptional.get();
        userManager.deleteGroceries(groceries);
    }
}
