package pickyeater.UI.app.foodpage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.executors.ExecutorProvider;
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
    private JButton btSearchIngredient;
    private JButton btAddIngredient;
    private JTextField textField1;
    private JLabel txtQuantityType;
    private JComboBox cbQuantityType;

    public CreateIngredient() {
        btDailyProgress.setBackground(Color.white);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.green);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.white);
        btSettings.setBackground(Color.white);

        ExecutorProvider executorProvider = new ExecutorProvider();
        IngredientSearcherExecutor ingredientSearcherExecutor =
                new IngredientSearcherExecutor(executorProvider.getEaterManager());

        listIngredients.setListData(ingredientSearcherExecutor.getAllIngredientsObj());
        txtQuantityType.setText("g");

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
        btSearchIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new FoodPage();
            }
        });
        btAddIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Save ingredient to database
                setVisible(false);
                new CreateIngredient();
            }
        });
        cbQuantityType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbQuantityType.getSelectedIndex() == 0){
                    // todo: do stuff
                    txtQuantityType.setText("g");
                } else if (cbQuantityType.getSelectedIndex() == 1) {
                    // todo: do stuff
                    txtQuantityType.setText("ml");
                } else if (cbQuantityType.getSelectedIndex() == 2) {
                    // todo: do stuff
                    txtQuantityType.setText("pz");
                }
            }
        });
    }

}
