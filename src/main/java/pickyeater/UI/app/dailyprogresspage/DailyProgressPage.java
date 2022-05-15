package pickyeater.UI.app.dailyprogresspage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.executors.DailyProgressExecutor;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.user.UserMealsProgressesExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

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

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        DailyProgressExecutor dailyProgressExecutor = ExecutorProvider.getDailyProgressExecutor();

        cbConsumed.insertItemAt("Calories: " + dailyProgressExecutor.getEatenCalories() + "/" + dailyProgressExecutor.getCaloriesToEat(), 0);
        cbConsumed.insertItemAt("Proteins: " + dailyProgressExecutor.getEatenProteins() + "/" + dailyProgressExecutor.getProteinsToEat(), 1);
        cbConsumed.insertItemAt("Carbs: " + dailyProgressExecutor.getEatenCarbs() + "/" + dailyProgressExecutor.getCarbsToEat(), 2);
        cbConsumed.insertItemAt("Fats: " + dailyProgressExecutor.getEatenFats() + "/" + dailyProgressExecutor.getFatsToEat(), 3);

        cbConsumed.setSelectedIndex(0);

        txtBurntCalories.setText(Integer.toString(dailyProgressExecutor.getBurntCalories()));

        listEatenMeals.setListData(dailyProgressExecutor.getAllMealsObj());

        progressBar(dailyProgressExecutor.getEatenCalories(), dailyProgressExecutor.getCaloriesToEat());

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
        cbConsumed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbConsumed.getSelectedIndex() == 0){
                    progressBar(dailyProgressExecutor.getEatenCalories(), dailyProgressExecutor.getCaloriesToEat());
                } else if (cbConsumed.getSelectedIndex() == 1){
                    progressBar(dailyProgressExecutor.getEatenProteins(), dailyProgressExecutor.getProteinsToEat());
                } else if (cbConsumed.getSelectedIndex() == 2){
                    progressBar(dailyProgressExecutor.getEatenCarbs(), dailyProgressExecutor.getCarbsToEat());
                } else if (cbConsumed.getSelectedIndex() == 3){
                    progressBar(dailyProgressExecutor.getEatenFats(), dailyProgressExecutor.getFatsToEat());
                }
            }
        });
    }
    private void progressBar(float eaten, float toEat){
        float percentage = (eaten / toEat) * 100;

        bar.setStringPainted(true);
        bar.setValue((int)percentage);
        DecimalFormat df = new DecimalFormat("0.00");
        bar.setString(df.format(percentage) + "%");
        setVisible(true);
    }
}
