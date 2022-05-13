package pickyeater.UI.app.foodpage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.database.PickyEatersDatabase;
import pickyeater.executors.ExecutorProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class FoodPage extends JFrame {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JList listMeals;
    private JList listIngredients;
    private JButton btSearchMeal;
    private JButton btSearchIngredient;
    private JButton btAddMeal;
    private JButton btAddIngredient;

    public FoodPage() {
        btDailyProgress.setBackground(Color.white);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.green);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.white);
        btSettings.setBackground(Color.white);

        listMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listIngredients.setSelectionMode((ListSelectionModel.SINGLE_SELECTION));

        // TODO: PUT IN AN EXECUTOR
        ExecutorProvider executorProvider = new ExecutorProvider();

       // listMeals.setListData();

        Set<Ingredient> ingredientSet = executorProvider.getEaterManager().getFoodManager().getIngredients();
        int tmpSize = ingredientSet.size();
        Object o1[] = new Object[tmpSize];
        for (Iterator<Ingredient> it = ingredientSet.iterator(); it.hasNext(); tmpSize--) {
            Ingredient ingredient = it.next();
            o1[tmpSize - 1] = ingredient.getName();
        }
        listIngredients.setListData(o1);

        Set<Meal> mealSet = executorProvider.getEaterManager().getFoodManager().getMeals();
        tmpSize = mealSet.size();
        Object o2[] = new Object[tmpSize];
        for (Iterator<Meal> it = mealSet.iterator(); it.hasNext(); tmpSize--) {
            Meal meal = it.next();
            o2[tmpSize - 1] = meal.getName();
        }
        listMeals.setListData(o2);

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

        btSearchMeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SearchMeal();
            }
        });
    }
}
