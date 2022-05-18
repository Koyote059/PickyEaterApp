package pickyeater.UI.app.foodpage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.QuantityType;
import pickyeater.builders.IngredientBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.creators.CreateIngredientExecutor;
import pickyeater.executors.searcher.IngredientSearcherExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateIngredient extends JFrame {
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

    public CreateIngredient() {
        btDailyProgress.setBackground(Color.white);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.green);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.white);
        btSettings.setBackground(Color.white);

        quantityType = QuantityType.GRAMS;

        CreateIngredientExecutor createIngredientExecutor = ExecutorProvider.getCreateIngredientExecutor();
        IngredientSearcherExecutor ingredientSearcherExecutor = ExecutorProvider.getIngredientSearcherExecutor();

        listIngredients.setListData(ingredientSearcherExecutor.getAllIngredientsObj());

        txtQuantity.setVisible(false);
        tfQuantity.setVisible(false);
        txtQuantityType.setVisible(false);

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
                new FoodPage();
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IngredientBuilder ingredientBuilder = createIngredientExecutor.createIngredient(tfName.getText(),
                        quantityType, tfQuantity.getText(), tfPrice.getText(), tfProteins.getText(),
                        tfCarbs.getText(), tfFats.getText());

                createIngredientExecutor.saveIngredient(ingredientBuilder.build());

                setVisible(false);
                new CreateIngredient();
            }
        });
        cbQuantityType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }

}
