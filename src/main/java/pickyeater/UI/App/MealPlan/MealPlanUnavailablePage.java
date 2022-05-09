package pickyeater.UI.App.MealPlan;

/**
 * @author Claudio Di Maio
 */

import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.UserMealsProgressesExecutor;

import javax.swing.*;

public class MealPlanUnavailablePage extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton clickHereToGoButton;
    UserMealsProgressesExecutor userMealsProgressesExecutor;

    public MealPlanUnavailablePage(ExecutorProvider executorProvider) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        this.userMealsProgressesExecutor = executorProvider.getUserMealsProgressesExecutor();


    }
}
