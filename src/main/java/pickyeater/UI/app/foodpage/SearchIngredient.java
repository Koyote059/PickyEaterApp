package pickyeater.UI.app.foodpage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Ingredient;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.IngredientSearcherExecutor;

import java.text.DecimalFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchIngredient extends JFrame {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JList listIngredients;
    private JButton btDone;
    private JPanel mainPanel;
    private JLabel txtCalories;
    private JLabel txtFats;
    private JLabel txtCarbs;
    private JLabel txtProteins;
    private JLabel txtIngredientStats;
    private JPanel panelPieChart;

    public SearchIngredient() {
        btDailyProgress.setBackground(Color.white);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.green);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.white);
        btSettings.setBackground(Color.white);

        ExecutorProvider executorProvider = new ExecutorProvider();
        listIngredients.setListData(new IngredientSearcherExecutor(executorProvider.getEaterManager()).getAllIngredientsObj());

        IngredientSearcherExecutor ingredientSearcherExecutor =
                new IngredientSearcherExecutor(executorProvider.getEaterManager());

        /*
        // TODO: ingredient = first ingredient in index
        txtIngredientStats.setText(meal.getName() + " stats");
        txtCalories.setText(Double.toString(meal.getNutrients().getCalories()));
        txtCarbs.setText(Double.toString(meal.getNutrients().getCarbs()));
        txtProteins.setText(Double.toString(meal.getNutrients().getProteins()));
        txtFats.setText(Double.toString(meal.getNutrients().getFats()));
         */

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
        btDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new FoodPage();
            }
        });
        listIngredients.addComponentListener(new ComponentAdapter() {
        });
        listIngredients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                Ingredient ingredient = ingredientSearcherExecutor.getIngredientWithName((String) listIngredients.getSelectedValue());

                DecimalFormat df = new DecimalFormat("0.000");

                txtIngredientStats.setText(ingredient.getName() + " stats");
                txtCalories.setText(df.format(ingredient.getNutrients().getCalories()));
                txtCarbs.setText(df.format(ingredient.getNutrients().getCarbs()));
                txtProteins.setText(df.format(ingredient.getNutrients().getProteins()));
                txtFats.setText(df.format(ingredient.getNutrients().getFats()));

                //panelPieChart
            }
        });
    }
}
