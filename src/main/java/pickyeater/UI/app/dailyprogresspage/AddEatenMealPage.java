package pickyeater.UI.app.dailyprogresspage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.MealSearcherExecutor;
import pickyeater.managers.EaterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEatenMealPage extends JFrame {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton btCancel;
    private JPanel pieChartPanel;
    private JList listMeals;
    private JPanel mainPanel;
    private JButton btSave;
    private JTextField tfQuantity;

    public AddEatenMealPage() {

        btDailyProgress.setBackground(Color.green);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.white);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.white);
        btSettings.setBackground(Color.white);

        listMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);    //TODO. For now
        listMeals.setListData(new MealSearcherExecutor(ExecutorProvider.getEaterManager()).getAllMealsObj());

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
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DailyProgressPage();
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // TODO: Do stuff but stay in the same page
            }
        });
    }
}