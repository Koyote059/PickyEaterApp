package pickyeater.UI.pages.choosers;

import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.Quantity;
import pickyeater.utils.IngredientQuantityConverter;
import pickyeater.utils.ValuesConverter;
import pickyeater.utils.pagesutils.NutrientsPieChart;

import javax.swing.*;
import java.awt.*;

import static pickyeater.basics.food.QuantityType.PIECES;

public class IngredientInfoJDialog extends JDialog {
    private JLabel labelName;
    private JLabel labelQuantityType;
    private JLabel labelGramsQuantity;
    private JLabel labelGramsPerQuantity;
    private JLabel labelProteins;
    private JLabel labelPrice;
    private JLabel labelCarbs;
    private JLabel labelFats;
    private JLabel priceLabel;
    private JLabel nutrientsLabel;

    public IngredientInfoJDialog(JFrame parent, Ingredient ingredient, Point location) {
        super(parent, ingredient.getName() + " - Info", true);
        Quantity quantity = ingredient.getQuantity();
        if (quantity.getQuantityType().equals(PIECES)) {
            IngredientQuantityConverter converter = new IngredientQuantityConverter();
            ingredient = converter.convert(ingredient, 1);
        }
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> this.dispose());
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = fillIngredientInfoPanel(ingredient, quantity);
        NutrientsPieChart nutrientsPieChart = new NutrientsPieChart();
        nutrientsPieChart.setName(ingredient.getName());
        nutrientsPieChart.setNutrients(ingredient.getNutrients());
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        JPanel chartPanel = nutrientsPieChart.getPanel();
        chartPanel.setMaximumSize(new Dimension(100, 100));
        mainPanel.add(chartPanel, BorderLayout.LINE_START);
        mainPanel.add(doneButton, BorderLayout.PAGE_END);
        setContentPane(mainPanel);
        pack();
        setSize(new Dimension(677, 507));
        setResizable(false);
        setLocation(location);
    }

    private JPanel fillIngredientInfoPanel(Ingredient ingredient, Quantity quantity) {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 1, 5, 3);
        JLabel nameLabel = new JLabel("Name: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        infoPanel.add(nameLabel, constraints);
        labelName = new JLabel(ingredient.getName());
        labelName.setHorizontalAlignment(JTextField.LEFT);
        constraints.gridx = 1;
        constraints.gridy = 0;
        infoPanel.add(labelName, constraints);
        JLabel quantityTypeLabel = new JLabel("Type: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        infoPanel.add(quantityTypeLabel, constraints);
        labelGramsQuantity = new JLabel("Grams: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        infoPanel.add(labelGramsQuantity, constraints);
        labelGramsPerQuantity = new JLabel();
        labelGramsPerQuantity.setVisible(false);
        labelGramsQuantity.setVisible(false);
        constraints.gridx = 1;
        constraints.gridy = 2;
        infoPanel.add(labelGramsPerQuantity, constraints);
        priceLabel = new JLabel("Price: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        infoPanel.add(priceLabel, constraints);
        labelPrice = new JLabel(ValuesConverter.convertFloat(ingredient.getPrice()) + " €");
        constraints.gridx = 1;
        constraints.gridy = 3;
        infoPanel.add(labelPrice, constraints);
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
        labelProteins = new JLabel(ValuesConverter.convertFloat(nutrients.getProteins()));
        constraints.gridx = 1;
        constraints.gridy = 6;
        infoPanel.add(labelProteins, constraints);
        JLabel carbsLabel = new JLabel("Carbs: ");
        constraints.gridx = 0;
        constraints.gridy = 7;
        infoPanel.add(carbsLabel, constraints);
        labelCarbs = new JLabel(ValuesConverter.convertFloat(nutrients.getCarbs()));
        constraints.gridx = 1;
        constraints.gridy = 7;
        infoPanel.add(labelCarbs, constraints);
        JLabel fatsLabel = new JLabel("Fats: ");
        constraints.gridx = 0;
        constraints.gridy = 8;
        infoPanel.add(fatsLabel, constraints);
        labelFats = new JLabel(ValuesConverter.convertFloat(nutrients.getFats()));
        constraints.gridx = 1;
        constraints.gridy = 8;
        infoPanel.add(labelFats, constraints);
        labelQuantityType = new JLabel();
        switch (quantity.getQuantityType()) {
            case GRAMS:
                labelGramsPerQuantity.setVisible(false);
                labelGramsQuantity.setVisible(false);
                labelQuantityType.setText("Grams");
                priceLabel.setText("Price: ");
                nutrientsLabel.setText("Nutrients per 100 g: ");
                break;
            case PIECES:
                labelGramsPerQuantity.setVisible(true);
                labelGramsPerQuantity.setText(ValuesConverter.convertFloat(quantity.getGramsPerQuantity()) + " g");
                labelGramsQuantity.setVisible(true);
                labelQuantityType.setText("Piece");
                labelGramsQuantity.setText("Grams: ");
                priceLabel.setText("Price: ");
                nutrientsLabel.setText("Nutrients per piece: ");
                break;
            case MILLILITERS:
                labelGramsPerQuantity.setVisible(true);
                labelGramsQuantity.setVisible(true);
                labelQuantityType.setText("Milliliters");
                labelGramsPerQuantity.setText(ValuesConverter.convertFloat(quantity.getGramsPerQuantity() * 100) + " g");
                labelGramsQuantity.setText("Grams: ");
                priceLabel.setText("Price: ");
                nutrientsLabel.setText("Nutrients per 100 ml: ");
                break;
        }
        constraints.gridx = 1;
        constraints.gridy = 1;
        infoPanel.add(labelQuantityType, constraints);
        return infoPanel;
    }
}
