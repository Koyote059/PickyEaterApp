package pickyeater.UI.app.foodpage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.executors.searcher.MealSearcherExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateMeal extends JFrame {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JList listMeals;
    private JButton btCancel;
    private JButton btSave;
    private JButton btAddIngredient;
    private JTextField tfName;
    private JList listIngredients;

    public CreateMeal() {
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#B1EA9D"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        ExecutorProvider executorProvider = new ExecutorProvider();

        IngredientSearcherExecutor ingredientSearcherExecutor =
                new IngredientSearcherExecutor(executorProvider.getEaterManager());
        MealSearcherExecutor mealSearcherExecutor = new MealSearcherExecutor(executorProvider.getEaterManager());

        listIngredients.setListData(ingredientSearcherExecutor.getAllIngredientsObj());
        listMeals.setListData(mealSearcherExecutor.getAllMealsObj());



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
                new MainButton(PanelButtons.FOOD);
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainButton(PanelButtons.FOOD);
            }
        });
        btAddIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new CreateIngredient();
            }
        });
    }
}
