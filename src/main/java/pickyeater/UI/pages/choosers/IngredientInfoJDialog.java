package pickyeater.UI.pages.choosers;

import pickyeater.UI.pages.utils.NutrientsPieChart;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Quantity;
import pickyeater.utils.ValuesConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        add(BorderLayout.PAGE_END,doneButton);
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

        Quantity quantity = ingredient.getQuantity();
        String quantitySuffix = ValuesConverter.convertQuantityTypeValue(quantity.getQuantityType());

        ingredientInfoPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        ingredientInfoPanel.add(new JLabel("Price per " + quantitySuffix),constraints);
        constraints.gridx = 1;
        ingredientInfoPanel.add(new JLabel(String.valueOf(ingredient.getPrice())));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx=2;
        ingredientInfoPanel.add(new JLabel("Nutrients per " + quantitySuffix),constraints);

    }


}
