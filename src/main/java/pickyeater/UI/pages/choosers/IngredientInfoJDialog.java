package pickyeater.UI.pages.choosers;

import pickyeater.utils.pagesutils.NutrientsPieChart;
import pickyeater.basics.food.*;
import pickyeater.utils.IngredientQuantityConverter;
import pickyeater.utils.ValuesConverter;

import javax.swing.*;
import java.awt.*;

import static pickyeater.basics.food.QuantityType.*;

public class IngredientInfoJDialog extends JDialog {
    private JTextField nameTextField;
    private JTextField quantityTypeField;
    private JLabel gramsQuantityLabel;
    private JTextField gramsPerQuantityTextField;
    private JTextField proteinsTextField;
    private JTextField priceTextField;
    private JTextField carbsTextField;
    private JTextField fatsTextField;
    private JLabel priceLabel;

    private JLabel nutrientsLabel;
    private Ingredient startingIngredient = null;
    private boolean isIngredientEditing = false;

    public IngredientInfoJDialog(JFrame parent, Ingredient ingredient, Point location) {
        super(parent, "IngredientCreator", true);
        Quantity quantity = ingredient.getQuantity();
        if(quantity.getQuantityType().equals(PIECES)) {
            IngredientQuantityConverter converter = new IngredientQuantityConverter();
            ingredient = converter.convert(ingredient,1);
        }

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> this.dispose());
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = fillIngredientInfoPanel(ingredient,quantity);
        NutrientsPieChart nutrientsPieChart = new NutrientsPieChart();
        nutrientsPieChart.setName(ingredient.getName());
        nutrientsPieChart.setNutrients(ingredient.getNutrients());
        mainPanel.add(infoPanel,BorderLayout.CENTER);
        JPanel chartPanel = nutrientsPieChart.getPanel();
        chartPanel.setMaximumSize(new Dimension(100,100));
        mainPanel.add(chartPanel,BorderLayout.LINE_START);
        mainPanel.add(doneButton,BorderLayout.PAGE_END);
        setContentPane(mainPanel);
        pack();
        setSize(new Dimension(677, 507));
        setResizable(false);
        setLocation(location);
    }

    private JPanel fillIngredientInfoPanel(Ingredient ingredient,Quantity quantity){
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 1, 5, 3);
        JLabel nameLabel = new JLabel("Name: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        infoPanel.add(nameLabel, constraints);
        nameTextField = new JTextField(ingredient.getName());
        nameTextField.setHorizontalAlignment(JTextField.LEFT);
        nameTextField.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        infoPanel.add(nameTextField, constraints);
        JLabel quantityTypeLabel = new JLabel("Quantity type: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        infoPanel.add(quantityTypeLabel, constraints);
        gramsQuantityLabel = new JLabel("Grams per piece: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        infoPanel.add(gramsQuantityLabel, constraints);
        gramsPerQuantityTextField = new JTextField();

        gramsPerQuantityTextField.setEditable(false);
        gramsPerQuantityTextField.setVisible(false);
        gramsQuantityLabel.setVisible(false);
        constraints.gridx = 1;
        constraints.gridy = 2;
        infoPanel.add(gramsPerQuantityTextField, constraints);
        priceLabel = new JLabel("Price per 100 grams: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        infoPanel.add(priceLabel, constraints);
        priceTextField = new JTextField(ValuesConverter.convertFloat(ingredient.getPrice()));
        priceTextField.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 3;
        infoPanel.add(priceTextField, constraints);
        JSeparator separator = new JSeparator();
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        infoPanel.add(separator, constraints);
        nutrientsLabel = new JLabel("Nutrients per 100 gram: ");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        infoPanel.add(nutrientsLabel, constraints);
        constraints.gridwidth = 1;
        JLabel proteinsLabel = new JLabel("Proteins: ");
        constraints.gridx = 0;
        constraints.gridy = 6;
        infoPanel.add(proteinsLabel, constraints);
        Nutrients nutrients = ingredient.getNutrients();
        proteinsTextField = new JTextField(ValuesConverter.convertFloat(nutrients.getProteins()));
        proteinsTextField.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 6;
        infoPanel.add(proteinsTextField, constraints);
        JLabel carbsLabel = new JLabel("Carbs: ");
        constraints.gridx = 0;
        constraints.gridy = 7;
        infoPanel.add(carbsLabel, constraints);
        carbsTextField = new JTextField(ValuesConverter.convertFloat(nutrients.getCarbs()));
        carbsTextField.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 7;
        infoPanel.add(carbsTextField, constraints);
        JLabel fatsLabel = new JLabel("Fats: ");
        constraints.gridx = 0;
        constraints.gridy = 8;
        infoPanel.add(fatsLabel, constraints);
        fatsTextField = new JTextField(ValuesConverter.convertFloat(nutrients.getFats()));
        fatsTextField.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 8;
        infoPanel.add(fatsTextField, constraints);

        quantityTypeField = new JTextField();
        quantityTypeField.setEditable(false);
        switch (quantity.getQuantityType()) {
            case GRAMS -> {
                gramsPerQuantityTextField.setVisible(false);
                gramsQuantityLabel.setVisible(false);
                quantityTypeField.setText("Grams");
                priceLabel.setText("Price per 100 g: ");
                nutrientsLabel.setText("Nutrients per 100 g: ");
            }
            case PIECES -> {
                gramsPerQuantityTextField.setVisible(true);
                gramsPerQuantityTextField.setText(ValuesConverter.convertFloat(quantity.getGramsPerQuantity()));
                gramsQuantityLabel.setVisible(true);
                quantityTypeField.setText("Pieces");
                gramsQuantityLabel.setText("Grams per piece: ");
                priceLabel.setText("Price per piece: ");
                nutrientsLabel.setText("Nutrients per piece: ");
            }
            case MILLILITERS -> {
                gramsPerQuantityTextField.setVisible(true);
                gramsQuantityLabel.setVisible(true);
                quantityTypeField.setText("Milliliters");
                gramsPerQuantityTextField.setText(ValuesConverter.convertFloat(quantity.getGramsPerQuantity()*100));
                gramsQuantityLabel.setText("Grams per 100 ml: ");
                priceLabel.setText("Price per 100ml: ");
                nutrientsLabel.setText("Nutrients per 100 ml: ");
            }
        }
        constraints.gridx = 1;
        constraints.gridy = 1;
        infoPanel.add(quantityTypeField, constraints);
        return infoPanel;
    }


}
