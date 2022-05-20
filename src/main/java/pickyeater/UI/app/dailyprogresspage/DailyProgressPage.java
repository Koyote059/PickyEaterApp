package pickyeater.UI.app.dailyprogresspage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.choosers.MealsChooser;
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Quantity;
import pickyeater.executors.DailyProgressExecutor;
import pickyeater.executors.ExecutorProvider;
import GARBAGE.UserMealsProgressesExecutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Optional;

public class DailyProgressPage extends JFrame {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton btAddEatenMeals;
    private JButton btAddBurntCalories;
    private JProgressBar bar;
    private JComboBox cbConsumed;
    private JLabel txtBurntCalories;
    private JTable tableEatenMeals;

    public DailyProgressPage() {
        btDailyProgress.setBackground(Color.decode("#B1EA9D"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        DailyProgressExecutor dailyProgressExecutor = ExecutorProvider.getDailyProgressExecutor();

        DecimalFormat df = new DecimalFormat();

        cbConsumed.insertItemAt("Calories: " + df.format(dailyProgressExecutor.getEatenCalories()) + "/" + df.format(dailyProgressExecutor.getCaloriesToEat()), 0);
        cbConsumed.insertItemAt("Proteins: " + df.format(dailyProgressExecutor.getEatenProteins()) + "/" + df.format(dailyProgressExecutor.getProteinsToEat()), 1);
        cbConsumed.insertItemAt("Carbs: " + df.format(dailyProgressExecutor.getEatenCarbs()) + "/" + df.format(dailyProgressExecutor.getCarbsToEat()), 2);
        cbConsumed.insertItemAt("Fats: " + df.format(dailyProgressExecutor.getEatenFats()) + "/" + df.format(dailyProgressExecutor.getFatsToEat()), 3);

        cbConsumed.setSelectedIndex(0);

        txtBurntCalories.setText(Integer.toString(dailyProgressExecutor.getBurntCalories()));

        // TODO: Show quantity next to name
        tableEatenMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        draw();

        progressBar(dailyProgressExecutor.getEatenCalories(), dailyProgressExecutor.getCaloriesToEat());

        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            new MainButton(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);

        btAddEatenMeals.addActionListener(e -> {
            MealsChooser chooser = new MealsChooser(this);
            Optional<Meal> mealOptional = chooser.getMeal();
            if(mealOptional.isEmpty()) return;
            dailyProgressExecutor.addEatenMeal(mealOptional.get());
            draw();
            //listEatenMeals.setListData(dailyProgressExecutor.getAllMealsObj());
        });
        btAddBurntCalories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddBurntCaloriesPage();
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

    private void draw() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Quantity");

        DailyProgressExecutor dailyProgressExecutor = ExecutorProvider.getDailyProgressExecutor();
        for (Meal meal : dailyProgressExecutor.getEatenMeals()) {
            float ingredientQuantity = meal.getWeight();
            Object[]  row= new Object[]{
                    meal.getName(), meal.getWeight()
            };
            model.addRow(row);
        }

        tableEatenMeals.setModel(model);
    }
}
