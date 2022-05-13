package pickyeater.UI.app.dailyprogresspage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.executors.UserMealsProgressesExecutor;

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
    private JProgressBar bar;
    private JComboBox cbConsumed;
    private JLabel txtBurntCalories;

    UserMealsProgressesExecutor userMealsProgressesExecutor;

    public DailyProgressPage() {
        btDailyProgress.setBackground(Color.green);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.white);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.white);
        btSettings.setBackground(Color.white);

        //bar.setStringPainted(true);
        //bar.setValue(0-100);
        //bar.setString("Done");

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                setVisible(false);
                new MainButton(new PanelButtonsConverter(cmd).Convert());
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
                new AddEatenMealPage();
            }
        });
        btAddBurntCalories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddBurntCalories();
            }
        });
    }
}
