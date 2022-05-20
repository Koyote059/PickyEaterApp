package pickyeater.UI.app.foodpage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Meal;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.MealSearcherExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Optional;

public class SearchMeal extends PickyPage {
    private final MealSearcherExecutor mealSearcherExecutor;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JList listMeals;
    private JButton btDone;
    private JPanel mainPanel;
    private JLabel txtCalories;
    private JLabel txtFats;
    private JLabel txtCarbs;
    private JLabel txtProteins;
    private JLabel txtMealStats;
    private JPanel panelPieChart;

    public SearchMeal(JFrame parent) {
        super(parent);
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#B1EA9D"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        add(mainPanel);

        mealSearcherExecutor = ExecutorProvider.getMealSearcherExecutor();

        /*
        // TODO: meal = first meal in index
        txtMealStats.setText(meal.getName() + " stats");
        txtCalories.setText(Double.toString(meal.getNutrients().getCalories()));
        txtCarbs.setText(Double.toString(meal.getNutrients().getCarbs()));
        txtProteins.setText(Double.toString(meal.getNutrients().getProteins()));
        txtFats.setText(Double.toString(meal.getNutrients().getFats()));
         */

        setNavigationMenuListeners();
        btDone.addActionListener(e -> {
            MainFrame.changePage(PanelButtons.FOOD);
        });
        listMeals.addComponentListener(new ComponentAdapter() {
        });
        listMeals.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Optional<Meal> mealOptional =
                        mealSearcherExecutor.getMealByName((String) listMeals.getSelectedValue());
                if(mealOptional.isEmpty()){
                    throw new RuntimeException("Missing ingredient from database : " + listMeals.getSelectedValue());
                } else {
                Meal meal = mealOptional.get();
                DecimalFormat df = new DecimalFormat("0.000");
                txtMealStats.setText(meal.getName());
                txtCalories.setText(df.format(meal.getNutrients().getCalories()));
                txtCarbs.setText(df.format(meal.getNutrients().getCarbs()));
                txtProteins.setText(df.format(meal.getNutrients().getProteins()));
                txtFats.setText(df.format(meal.getNutrients().getFats()));
            }
            }
        });
    }

    private void setNavigationMenuListeners(){
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            MainFrame.changePage(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btDiet.addActionListener(listener);
    }

    @Override
    public void showPage() {
        listMeals.setListData(mealSearcherExecutor.getAllMealsObj());
        super.showPage();
    }
}
