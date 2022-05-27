package pickyeater.UI.app.dailyprogresspage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.choosers.MealsChooser;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Meal;
import pickyeater.executors.DailyProgressExecutor;
import pickyeater.executors.ExecutorProvider;
import pickyeater.themes.ColorButtons;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Optional;

public class DailyProgressPage extends PickyPage {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JButton btAddEatenMeals;
    private JButton btAddBurntCalories;
    private JTable tableEatenMeals;
    private JProgressBar bar;
    private JComboBox cbConsumed;
    private JLabel txtBurntCalories;
    DailyProgressExecutor dailyProgressExecutor;
    JFrame parent;

    int burntCalories;

    public DailyProgressPage(JFrame parent) {
        super(parent);
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        new ColorButtons().ColorLeftButtons(btDailyProgress, btDiet, btGroceries, btUser, btSettings);
        dailyProgressExecutor = ExecutorProvider.getDailyProgressExecutor();
        burntCalories = dailyProgressExecutor.getBurntCalories();
        btAddEatenMeals.addActionListener(e -> {
            MealsChooser chooser = new MealsChooser(parent);
            Optional<Meal> mealOptional = chooser.getMeal();
            if(mealOptional.isEmpty()) return;
            dailyProgressExecutor.addEatenMeal(mealOptional.get());
            draw();
        });
        btAddBurntCalories.addActionListener(e -> {
            setVisible(false);
            AddBurntCaloriesPage addBurntCaloriesPage = new AddBurntCaloriesPage(parent);
            addBurntCaloriesPage.run();
            draw();
        });
        cbConsumed.addActionListener(e -> {
            if (cbConsumed.getSelectedIndex() == 0){
                progressBar(dailyProgressExecutor.getEatenCalories(), dailyProgressExecutor.getCaloriesToEat());
            } else if (cbConsumed.getSelectedIndex() == 1){
                progressBar(dailyProgressExecutor.getEatenProteins(), dailyProgressExecutor.getProteinsToEat());
            } else if (cbConsumed.getSelectedIndex() == 2){
                progressBar(dailyProgressExecutor.getEatenCarbs(), dailyProgressExecutor.getCarbsToEat());
            } else if (cbConsumed.getSelectedIndex() == 3){
                progressBar(dailyProgressExecutor.getEatenFats(), dailyProgressExecutor.getFatsToEat());
            }
        });

        tableEatenMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setNavigationMenuListeners();
    }
    private void progressBar(float eaten, float toEat){
        float percentage = (eaten / toEat) * 100;

        bar.setStringPainted(true);
        bar.setValue((int)percentage);
        DecimalFormat df = new DecimalFormat("0.00");
        bar.setString(df.format(percentage) + "%");
    }

    private void setNavigationMenuListeners(){
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            MainFrame.changePage(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btDiet.addActionListener(listener);
        btSettings.setSize(new Dimension(200,85));
        btUser.setSize(new Dimension(200,85));
        btGroceries.setSize(new Dimension(200,85));
        btDiet.setSize(new Dimension(200,85));

    }

    private void draw() {
        DecimalFormat df = new DecimalFormat("0.00");
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Quantity");

        DailyProgressExecutor dailyProgressExecutor = ExecutorProvider.getDailyProgressExecutor();
        for (Meal meal : dailyProgressExecutor.getEatenMeals()) {
            float mealQuantity = meal.getWeight();
            Object[]  row= new Object[]{
                    meal.getName(), df.format(mealQuantity)
            };
            model.addRow(row);
        }

        txtBurntCalories.setText(String.valueOf(dailyProgressExecutor.getBurntCalories()));
        tableEatenMeals.setModel(model);

        cbConsumed.removeAllItems();
        cbConsumed.insertItemAt("Calories: " + df.format(dailyProgressExecutor.getEatenCalories()) + "/" + df.format(dailyProgressExecutor.getCaloriesToEat()), 0);
        cbConsumed.insertItemAt("Proteins: " + df.format(dailyProgressExecutor.getEatenProteins()) + "/" + df.format(dailyProgressExecutor.getProteinsToEat()), 1);
        cbConsumed.insertItemAt("Carbs: " + df.format(dailyProgressExecutor.getEatenCarbs()) + "/" + df.format(dailyProgressExecutor.getCarbsToEat()), 2);
        cbConsumed.insertItemAt("Fats: " + df.format(dailyProgressExecutor.getEatenFats()) + "/" + df.format(dailyProgressExecutor.getFatsToEat()), 3);
        cbConsumed.setSelectedIndex(0);
        progressBar(dailyProgressExecutor.getEatenCalories(), dailyProgressExecutor.getCaloriesToEat());
    }

    @Override
    public void showPage(){
        DecimalFormat df = new DecimalFormat("0.00");

        draw();
        super.showPage();
    }
}
