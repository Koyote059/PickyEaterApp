package pickyeater.UI;

/**
 * @author Claudio Di Maio
 */
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.UI.registerpage.Register1;
import pickyeater.basics.food.PickyIngredient;
import pickyeater.builders.PickyIngredientBuilder;
import pickyeater.database.*;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;

public class main {
    public static void main(String[] args) {

        //PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("PickyEatersDB.sqlite");
        PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("dbDiProva.sqlite");

        EaterManager eaterManager = new PickyEaterManager(
                pickyEatersDB.getUserDatabase(),
                pickyEatersDB.getIngredientsDatabase(),
                pickyEatersDB.getMealsDatabase());

        ExecutorProvider executorProvider = new ExecutorProvider(eaterManager);

        //eaterManager.getFoodManager().saveIngredient(new PickyIngredient());

        if (eaterManager.getUserManager().getUser().isEmpty()) {  // User Database is empty
            new Register1(eaterManager, executorProvider);
        } else {  // go to the app
            new MainButton(PanelButtons.PROGRESS);
        }
    }
}
