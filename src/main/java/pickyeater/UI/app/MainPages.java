package pickyeater.UI.app;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.managers.PickyEaterManager;

import javax.swing.*;

public class MainPages extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;

    public MainPages(PickyEaterManager pickyEaterManager, PanelButtons panelButton) {
        //setContentPane(mainPanel);
        //pack();
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setVisible(true);


    }
}
/*


        btDailyProgress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DailyProgressPage(executorProvider);
            }
        });
        btDiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MealPlanPage(executorProvider);
                //new MealPlanUnavailablePage(executorProvider);
                //new MealPlanUngeneratedPage(executorProvider);
            }
        });
        btFood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainPages(executorProvider);
            }
        });
        btGroceries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainPages(executorProvider);
            }
        });
        btUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new UserPage(executorProvider);
            }
        });
        btSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainPages(executorProvider);
            }
        });
    }
 */