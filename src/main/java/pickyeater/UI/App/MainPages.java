package pickyeater.UI.App;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.App.DailyProgressPage.DailyProgressPage;
import pickyeater.UI.App.MealPlanPage.MealPlanPage;
import pickyeater.UI.App.MealPlanPage.MealPlanUnavailablePage;
import pickyeater.UI.App.MealPlanPage.MealPlanUngeneratedPage;
import pickyeater.UI.App.UserPage.UserPage;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.UserMealsProgressesExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPages extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    UserMealsProgressesExecutor userMealsProgressesExecutor;

    public MainPages(ExecutorProvider executorProvider) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        this.userMealsProgressesExecutor = executorProvider.getUserMealsProgressesExecutor();
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
    }
}
