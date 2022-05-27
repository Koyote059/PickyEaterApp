package notusedpages.foodpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.QuantityType;
import pickyeater.builders.IngredientBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.creators.CreateIngredientExecutor;
import pickyeater.executors.searcher.IngredientSearcherExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreateIngredientPage extends PickyPage {

    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JList listIngredients;
    private JButton btCancel;
    private JButton btSave;
    private JTextField tfName;
    private JLabel txtQuantityType;
    private JComboBox cbQuantityType;
    private JTextField tfQuantity;
    private JLabel txtQuantity;
    private JTextField tfPrice;
    private JTextField tfProteins;
    private JTextField tfCarbs;
    private JTextField tfFats;
    private QuantityType quantityType;
    private IngredientSearcherExecutor ingredientSearcherExecutor;

    public CreateIngredientPage(JFrame parent) {
        super(parent);

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#B1EA9D"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        quantityType = QuantityType.GRAMS;

        CreateIngredientExecutor createIngredientExecutor = ExecutorProvider.getCreateIngredientExecutor();
        ingredientSearcherExecutor = ExecutorProvider.getIngredientSearcherExecutor();

        txtQuantity.setVisible(false);
        tfQuantity.setVisible(false);
        txtQuantityType.setVisible(false);

        setNavigationMenuListeners();
        //btCancel.addActionListener(e -> MainFrame.changePage(PanelButtons.FOOD));
        btSave.addActionListener(e -> {
            IngredientBuilder ingredientBuilder = createIngredientExecutor.createIngredient(tfName.getText(),
                    quantityType, tfQuantity.getText(), tfPrice.getText(), tfProteins.getText(),
                    tfCarbs.getText(), tfFats.getText());

            createIngredientExecutor.saveIngredient(ingredientBuilder.build());

            PickyPage createIngredientPage = new CreateIngredientPage(parent);
            createIngredientPage.showPage();
        });
        cbQuantityType.addActionListener(e -> {
            if (cbQuantityType.getSelectedIndex() == 0){
                txtQuantity.setVisible(false);
                tfQuantity.setVisible(false);
                txtQuantityType.setVisible(false);
                quantityType = QuantityType.GRAMS;
            } else if (cbQuantityType.getSelectedIndex() == 1) {
                txtQuantity.setVisible(true);
                tfQuantity.setVisible(true);
                txtQuantityType.setVisible(true);
                quantityType = QuantityType.MILLILITERS;
            } else if (cbQuantityType.getSelectedIndex() == 2) {
                txtQuantity.setVisible(true);
                tfQuantity.setVisible(true);
                txtQuantityType.setVisible(true);
                quantityType = QuantityType.PIECES;
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
        super.showPage();
    }
}
