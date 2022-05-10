package pickyeater.UI.App.UserPage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.UI.App.FoodPage.FoodPage;
import pickyeater.UI.App.GroceriesPage.GroceriesPage;
import pickyeater.UI.App.MainPages;
import pickyeater.UI.App.MealPlanPage.MealPlanPage;
import pickyeater.UI.App.SettingsPage.SettingsPage;
import pickyeater.UI.LeftButtons.MainButton;
import pickyeater.UI.LeftButtons.PanelButtons;
import pickyeater.UI.LeftButtons.PanelButtonsConverter;
import pickyeater.database.Databases;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.UserMealsProgressesExecutor;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPage extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton editModeButton;
    private JLabel txtName;
    private JLabel txtHeight;
    private JLabel txtWeight;
    private JLabel txtBodyFat;
    private JLabel txtSex;
    private JLabel txtDateOfBirth;
    private JLabel txtLifestyle;
    private JLabel txtWeightGoal;
    UserMealsProgressesExecutor userMealsProgressesExecutor;

    public UserPage(Databases databases) {    //TODO: EaterManager eaterManager,
        // TODO: txtName = ;
        //EaterManager eaterManager = new PickyEaterManager(PickyEaterManager)

        btDailyProgress.setBackground(Color.white);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.white);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.green);
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
                new MainButton(databases, new PanelButtonsConverter(cmd).Convert());
            }
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);
        editModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new UserEditModePage(databases);
            }
        });
    }
}
