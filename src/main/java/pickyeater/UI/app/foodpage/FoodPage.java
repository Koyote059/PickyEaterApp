package pickyeater.UI.app.foodpage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.executors.searcher.MealSearcherExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FoodPage extends PickyPage {

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

    public FoodPage(JFrame parent) {
        super(parent);

        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#B1EA9D"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        listMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listIngredients.setSelectionMode((ListSelectionModel.SINGLE_SELECTION));


        btSearchMeal.addActionListener(e -> {
            PickyPage searchMealPage = new SearchMeal(parent);
            searchMealPage.showPage();
        });
        btAddMeal.addActionListener(e -> {
            PickyPage createMealPage = new CreateMeal(parent);
            createMealPage.showPage();
        });
        btSearchIngredient.addActionListener(e -> {
            PickyPage searchIngredientPage = new SearchIngredient(parent);
            searchIngredientPage.showPage();
        });
        btAddIngredient.addActionListener(e -> {
            PickyPage pickyPage = new CreateIngredient(parent);
            pickyPage.showPage();
        });
        setNavigationMenuListeners();



        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
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
        btSettings.setSize(new Dimension(200,85));
        btUser.setSize(new Dimension(200,85));
        btGroceries.setSize(new Dimension(200,85));
        btFood.setSize(new Dimension(200,85));
        btDiet.setSize(new Dimension(200,85));
    }

    @Override
    public void showPage() {
        listIngredients.setListData(new IngredientSearcherExecutor(ExecutorProvider.getEaterManager()).getAllIngredientsObj());
        listMeals.setListData(new MealSearcherExecutor(ExecutorProvider.getEaterManager()).getAllMealsObj());
        super.showPage();
    }
}
