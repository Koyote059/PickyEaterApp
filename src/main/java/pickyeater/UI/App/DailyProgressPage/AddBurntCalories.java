package pickyeater.UI.App.DailyProgressPage;

import pickyeater.UI.App.MainPages;
import pickyeater.UI.App.MealPlanPage.MealPlanPage;
import pickyeater.UI.App.UserPage.UserPage;
import pickyeater.executors.ExecutorProvider;

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

    public AddBurntCalories(ExecutorProvider executorProvider) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        btDailyProgress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btDailyProgress.setBackground(Color.green);
                btDiet.setBackground(Color.white);
                btFood.setBackground(Color.white);
                btGroceries.setBackground(Color.white);
                btUser.setBackground(Color.white);
                btSettings.setBackground(Color.white);
                setVisible(false);
                new DailyProgressPage(executorProvider);
            }
        });
        btDiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btDailyProgress.setBackground(Color.white);
                btDiet.setBackground(Color.green);
                btFood.setBackground(Color.white);
                btGroceries.setBackground(Color.white);
                btUser.setBackground(Color.white);
                btSettings.setBackground(Color.white);
                setVisible(false);
                new MealPlanPage(executorProvider);
                //new MealPlanUnavailablePage(executorProvider);
                //new MealPlanUngeneratedPage(executorProvider);
            }
        });
        btFood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btDailyProgress.setBackground(Color.white);
                btDiet.setBackground(Color.white);
                btFood.setBackground(Color.green);
                btGroceries.setBackground(Color.white);
                btUser.setBackground(Color.white);
                btSettings.setBackground(Color.white);
                setVisible(false);
                new MainPages(executorProvider);
            }
        });
        btGroceries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btDailyProgress.setBackground(Color.white);
                btDiet.setBackground(Color.white);
                btFood.setBackground(Color.white);
                btGroceries.setBackground(Color.green);
                btUser.setBackground(Color.white);
                btSettings.setBackground(Color.white);
                setVisible(false);
                new MainPages(executorProvider);
            }
        });
        btUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btDailyProgress.setBackground(Color.white);
                btDiet.setBackground(Color.white);
                btFood.setBackground(Color.white);
                btGroceries.setBackground(Color.white);
                btUser.setBackground(Color.green);
                btSettings.setBackground(Color.white);
                setVisible(false);
                new UserPage(executorProvider);
            }
        });
        btSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btDailyProgress.setBackground(Color.white);
                btDiet.setBackground(Color.white);
                btFood.setBackground(Color.white);
                btGroceries.setBackground(Color.white);
                btUser.setBackground(Color.white);
                btSettings.setBackground(Color.green);
                setVisible(false);
                new MainPages(executorProvider);
            }
        });
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DailyProgressPage(executorProvider);
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DailyProgressPage(executorProvider);
            }
        });
    }
}