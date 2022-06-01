package notusedpages.foodpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Ingredient;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.IngredientSearcherExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Optional;

public class SearchIngredientPage extends PickyPage {
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
    private final IngredientSearcherExecutor ingredientSearcherExecutor;

    public SearchIngredientPage(JFrame parent) {
        super(parent);
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#B1EA9D"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));
        ingredientSearcherExecutor = ExecutorProvider.getIngredientSearcherExecutor();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        setNavigationMenuListeners();
        //btDone.addActionListener(e -> MainFrame.changePage(PanelButtons.FOOD));
        listIngredients.addComponentListener(new ComponentAdapter() {
        });
        listIngredients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Optional<Ingredient> ingredientOptional = ingredientSearcherExecutor.getIngredientByName((String) listIngredients.getSelectedValue());
                if (ingredientOptional.isEmpty()) {
                    throw new RuntimeException("Missing ingredient from database : " + listIngredients.getSelectedValue());
                } else {
                    Ingredient ingredient = ingredientOptional.get();
                    DecimalFormat df = new DecimalFormat("0.000");
                    txtIngredientStats.setText(ingredient.getName());
                    txtCalories.setText(df.format(ingredient.getNutrients().getCalories()));
                    txtCarbs.setText(df.format(ingredient.getNutrients().getCarbs()));
                    txtProteins.setText(df.format(ingredient.getNutrients().getProteins()));
                    txtFats.setText(df.format(ingredient.getNutrients().getFats()));
                }
                //panelPieChart
            }
        });
    }

    private void setNavigationMenuListeners() {
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
        listIngredients.setListData(ingredientSearcherExecutor.getAllIngredientsObj());
        super.showPage();
    }
}
