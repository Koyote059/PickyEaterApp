package pickyeater.UI.app.foodpage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Ingredient;
import pickyeater.builders.MealBuilder;
import pickyeater.builders.PickyMealBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.creators.CreateMealExecutor;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.executors.searcher.MealSearcherExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CreateMealPage extends PickyPage {
    private final MealSearcherExecutor mealSearcherExecutor;
    private final IngredientSearcherExecutor ingredientSearcherExecutor;
    private final CreateMealExecutor createMealExecutor;
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
    private JList listSelectedIngredients;

    public CreateMealPage(JFrame parent) {
        super(parent);

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#B1EA9D"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        ingredientSearcherExecutor = ExecutorProvider.getIngredientSearcherExecutor();
        mealSearcherExecutor = ExecutorProvider.getMealSearcherExecutor();
        createMealExecutor = ExecutorProvider.getCreateMealExecutor();

        Set<Ingredient> ingredientSelectedSet = new HashSet<>();

        setNavigationMenuListeners();

        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MealBuilder meal = new PickyMealBuilder();

                meal.setName(tfName.getText());
                createMealExecutor.addIngredients(meal, ingredientSelectedSet);

                createMealExecutor.saveMeal(meal.build());
                MainFrame.changePage(PanelButtons.FOOD);
            }
        });
        btCancel.addActionListener(e -> MainFrame.changePage(PanelButtons.FOOD));
        btAddIngredient.addActionListener(e -> {
            PickyPage createIngredientPage = new CreateIngredientPage(parent);
            createIngredientPage.showPage();
        });
        listIngredients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                createMealExecutor.appendToSet(ingredientSelectedSet, listIngredients.getSelectedValue().toString());
                listSelectedIngredients.setListData(createMealExecutor.getAllSelectedIngredientsObj(ingredientSelectedSet));
            }
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
