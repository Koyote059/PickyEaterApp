package pickyeater.UI.App;

import pickyeater.executors.RegisterExecutor;

import javax.swing.*;

public class UserPage extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JProgressBar progressBar1;
    private JComboBox comboBox1;
    private JButton btAddEatenMeals;
    private JButton btAddBurntCalories;
    private JList listEatenMeals;

    public UserPage(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
