package pickyeater.UI.App.DailyProgressPage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.executors.UserMealsProgressesExecutor;

import javax.swing.*;

public class DailyProgressPage extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;

    public DailyProgressPage(UserMealsProgressesExecutor userMealsProgressesExecutor) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
