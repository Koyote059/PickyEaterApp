package Garbage.DailyProgressPageWithColours;

/**
 * @author Claudio Di Maio
 */

import pickyeater.database.Databases;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.UserMealsProgressesExecutor;

import javax.swing.*;

public class DailyProgressPageWithColours extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton btAddEatenMeals;
    private JButton btAddBurntCalories;
    private JList listEatenMeals;
    private JProgressBar progressBar;
    private JComboBox cbConsumed;
    private JLabel txtBurntCalories;

    UserMealsProgressesExecutor userMealsProgressesExecutor;

    public DailyProgressPageWithColours(Databases databases) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //this.userMealsProgressesExecutor = executorProvider.getUserMealsProgressesExecutor();


    }
}
