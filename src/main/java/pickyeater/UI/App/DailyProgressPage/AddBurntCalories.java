package pickyeater.UI.App.DailyProgressPage;

import pickyeater.UI.App.FoodPage.FoodPage;
import pickyeater.UI.App.GroceriesPage.GroceriesPage;
import pickyeater.UI.App.MainPages;
import pickyeater.UI.App.MealPlanPage.MealPlanPage;
import pickyeater.UI.App.SettingsPage.SettingsPage;
import pickyeater.UI.App.UserPage.UserPage;
import pickyeater.UI.LeftButtons.MainButton;
import pickyeater.UI.LeftButtons.PanelButtonsConverter;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.PickyEaterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBurntCalories extends JFrame {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton btCancel;
    private JTextField textField1;
    private JPanel mainPanel;
    private JButton btSave;

    public AddBurntCalories(PickyEaterManager pickyEaterManager) {

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
        ExecutorProvider executorProvider = new ExecutorProvider(pickyEaterManager);
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

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DailyProgressPage(pickyEaterManager);
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DailyProgressPage(pickyEaterManager);
            }
        });
    }
}