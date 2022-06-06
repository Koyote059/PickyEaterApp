package pickyeater.UI.pages.choosers;

import pickyeater.UI.pages.utils.NutrientsPieChart;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.Quantity;
import pickyeater.basics.food.QuantityType;
import pickyeater.utils.IngredientQuantityConverter;
import pickyeater.utils.ValuesConverter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class IngredientInfoJDialog extends JDialog {

    private final Ingredient ingredient;
    private final NutrientsPieChart nutrientsPieChart;
    private final JPanel ingredientInfoPanel = new JPanel();
    private final JButton doneButton = new JButton("Done");

    public IngredientInfoJDialog(Frame parent, Ingredient ingredient) {
        super(parent, ingredient.getName() ,true);
        this.ingredient = ingredient;
        this.nutrientsPieChart = new NutrientsPieChart(ingredient.getNutrients(),ingredient.getName());
        setLayout(new BorderLayout());
        add(BorderLayout.CENTER,nutrientsPieChart.getPanel());
        add(BorderLayout.LINE_END,ingredientInfoPanel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(doneButton);
        add(BorderLayout.PAGE_END,buttonPanel);
        doneButton.setAlignmentX(Container.CENTER_ALIGNMENT);
        doneButton.setHorizontalAlignment(SwingConstants.CENTER);

        doneButton.addActionListener(e -> dispose());
        fillIngredientInfoPanel();
        setResizable(false);
        pack();
        setSize(new Dimension(677, 507));
        setLocationRelativeTo(parent);
    }

    private void fillIngredientInfoPanel(){

        Ingredient displayingIngredient;

        if(ingredient.getQuantity().getQuantityType().equals(QuantityType.PIECES)){
            displayingIngredient = new IngredientQuantityConverter().convert(ingredient,1);
        } else {
            displayingIngredient = ingredient;
        }

        Quantity quantity = displayingIngredient.getQuantity();
        String quantitySuffix = quantity.getAmount() + " " + ValuesConverter.convertQuantityTypeValue(quantity.getQuantityType());

        ingredientInfoPanel.setLayout(new GridBagLayout());
        ingredientInfoPanel.setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.ipadx = 1;
        JLabel priceLabel = new JLabel("Price per " + quantitySuffix +": ");
        priceLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        ingredientInfoPanel.add(priceLabel,constraints);
        constraints.gridx = 1;
        ingredientInfoPanel.add(new JLabel(ValuesConverter.convertFloat(displayingIngredient.getPrice())),constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx=3;
        Nutrients nutrients = displayingIngredient.getNutrients();
        ingredientInfoPanel.add(new JLabel("Nutrients per " + quantitySuffix + ": "),constraints);
        constraints.weightx = 1;
        constraints.gridy=2;
        constraints.gridx=1;
        ingredientInfoPanel.add(new JLabel("Proteins: "),constraints);
        constraints.gridx=2;
        ingredientInfoPanel.add(new JLabel(ValuesConverter.convertFloat(nutrients.getProteins())),constraints);
        constraints.gridy=3;
        constraints.gridx=1;
        ingredientInfoPanel.add(new JLabel("Carbs: "),constraints);
        constraints.gridx=2;
        ingredientInfoPanel.add(new JLabel(ValuesConverter.convertFloat(nutrients.getCarbs())),constraints);
        constraints.gridy=4;
        constraints.gridx=1;
        ingredientInfoPanel.add(new JLabel("Fats: "),constraints);
        constraints.gridx=2;
        ingredientInfoPanel.add(new JLabel(ValuesConverter.convertFloat(nutrients.getFats())),constraints);
        constraints.gridy=5;
        constraints.gridx=1;
        ingredientInfoPanel.add(new JLabel("Total calories: "),constraints);
        constraints.gridx=2;
        ingredientInfoPanel.add(new JLabel(ValuesConverter.convertFloat(nutrients.getCalories())),constraints);

        if(quantity.getQuantityType()==QuantityType.GRAMS) return;
        float weight = quantity.getGramsPerQuantity();
        if(quantity.getQuantityType().equals(QuantityType.MILLILITERS)) weight*=100;
        constraints.gridy=6;
        constraints.gridx=0;
        ingredientInfoPanel.add(new JLabel("Weight per " + quantitySuffix + ": "),constraints);
        constraints.gridx=1;
        ingredientInfoPanel.add(new JLabel(ValuesConverter.convertFloat(weight) + " g"),constraints);

    }


}
