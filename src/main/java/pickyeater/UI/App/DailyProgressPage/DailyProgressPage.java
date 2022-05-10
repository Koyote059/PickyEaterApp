package pickyeater.UI.App.DailyProgressPage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.App.FoodPage.FoodPage;
import pickyeater.UI.App.GroceriesPage.GroceriesPage;
import pickyeater.UI.App.MainPages;
import pickyeater.UI.App.MealPlanPage.MealPlanPage;
import pickyeater.UI.App.SettingsPage.SettingsPage;
import pickyeater.UI.App.UserPage.UserPage;
import pickyeater.UI.LeftButtons.MainButton;
import pickyeater.UI.LeftButtons.PanelButtonsConverter;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.UserMealsProgressesExecutor;
import pickyeater.managers.PickyEaterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DailyProgressPage extends JFrame{
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

    public DailyProgressPage(PickyEaterManager pickyEaterManager) {
        btDailyProgress.setBackground(Color.green);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.white);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.white);
        btSettings.setBackground(Color.white);

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                setVisible(false);
                new MainButton(pickyEaterManager, new PanelButtonsConverter(cmd).Convert());
            }
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);

        btAddEatenMeals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddEatenMealPage(pickyEaterManager);
            }
        });
        btAddBurntCalories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddBurntCalories(pickyEaterManager);
            }
        });
    }
}
