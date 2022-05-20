package pickyeater.UI.app.foodpage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
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

public class CreateMeal extends PickyPage {
    private final MealSearcherExecutor mealSearcherExecutor;
    private final IngredientSearcherExecutor ingredientSearcherExecutor;
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

    public CreateMeal(JFrame parent) {
        super(parent);
        add(mainPanel);
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#B1EA9D"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));


        ingredientSearcherExecutor = ExecutorProvider.getIngredientSearcherExecutor();
        mealSearcherExecutor = ExecutorProvider.getMealSearcherExecutor();


        setNavigationMenuListeners();
        btCancel.addActionListener(e -> MainFrame.changePage(PanelButtons.FOOD));
        btSave.addActionListener(e -> MainFrame.changePage(PanelButtons.FOOD));
        btAddIngredient.addActionListener(e -> {
            PickyPage createIngredientPage = new CreateIngredient(parent);
            createIngredientPage.showPage();
        });
    }

    private void setNavigationMenuListeners(){
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
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
        listIngredients.setListData(ingredientSearcherExecutor.getAllIngredientsObj());
        listMeals.setListData(mealSearcherExecutor.getAllMealsObj());
        super.showPage();
    }
}
