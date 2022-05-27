package pickyeater.UI.pages.creators;

import pickyeater.basics.food.*;
import pickyeater.builders.IngredientBuilder;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.creators.CreateIngredientExecutor;

import javax.swing.*;
import java.awt.*;

public class IngredientCreator extends JDialog {

    private JPanel mainPanel;
    private JTextField nameTextField;
    private JComboBox<String> quantityTypeBox;
    private JLabel gramsQuantityLabel;
    private JTextField gramsPerQuantityTextField;
    private JTextField proteinsTextField;
    private JTextField priceTextField;
    private JTextField carbsTextField;
    private JTextField fatsTextField;
    private JButton saveButton;
    private JButton cancelButton;
    private CreateIngredientExecutor executor = ExecutorProvider.getCreateIngredientExecutor();

    private Ingredient editingIngredient = null;

    public IngredientCreator(JFrame parent){
        super(parent,"IngredientCreator",true);
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.LINE_START;

        JLabel nameLabel = new JLabel("Name: ");
        constraints.gridx=0;
        constraints.gridy=0;
        mainPanel.add(nameLabel,constraints);

        nameTextField = new JTextField();
        constraints.gridx=1;
        constraints.gridy=0;
        mainPanel.add(nameTextField,constraints);

        JLabel quantityTypeLabel = new JLabel("Quantity type: ");
        constraints.gridx=0;
        constraints.gridy=1;
        mainPanel.add(quantityTypeLabel,constraints);

        quantityTypeBox = new JComboBox<>();
        quantityTypeBox.addItem("Grams");
        quantityTypeBox.addItem("Milliliters");
        quantityTypeBox.addItem("Pieces");
        quantityTypeBox.addActionListener( l -> {
            String selectedItem = (String) quantityTypeBox.getSelectedItem();
            if(selectedItem==null) return;
            switch (selectedItem){
                case "Grams" -> {
                    gramsPerQuantityTextField.setVisible(false);
                    gramsQuantityLabel.setVisible(false);
                }
                case "Milliliters","Pieces" -> {
                    gramsPerQuantityTextField.setVisible(true);
                    gramsQuantityLabel.setVisible(true);
                }
            }
            pack();
        });
        constraints.gridx=1;
        constraints.gridy=1;
        mainPanel.add(quantityTypeBox,constraints);


        gramsQuantityLabel = new JLabel("Grams per unit: ");
        constraints.gridx=0;
        constraints.gridy=2;
        mainPanel.add(gramsQuantityLabel,constraints);

        gramsPerQuantityTextField = new JTextField();
        gramsPerQuantityTextField.setVisible(false);
        gramsQuantityLabel.setVisible(false);
        constraints.gridx=1;
        constraints.gridy=2;
        mainPanel.add(gramsPerQuantityTextField,constraints);

        JLabel priceLabel = new JLabel("Price per quantity: ");
        constraints.gridx=0;
        constraints.gridy=3;
        mainPanel.add(priceLabel,constraints);

        priceTextField = new JTextField();
        constraints.gridx=1;
        constraints.gridy=3;
        mainPanel.add(priceTextField,constraints);


        JLabel nutrientsLabel = new JLabel("Nutrients per 100 gram: ");
        constraints.gridx=0;
        constraints.gridy=4;
        constraints.gridwidth=2;
        mainPanel.add(nutrientsLabel,constraints);

        constraints.gridwidth=1;

        JLabel proteinsLabel = new JLabel("Proteins: ");
        constraints.gridx=0;
        constraints.gridy=5;
        mainPanel.add(proteinsLabel,constraints);

        proteinsTextField = new JTextField();
        constraints.gridx=1;
        constraints.gridy=5;
        mainPanel.add(proteinsTextField,constraints);

        JLabel carbsLabel = new JLabel("Carbs: ");
        constraints.gridx=0;
        constraints.gridy=6;
        mainPanel.add(carbsLabel,constraints);

        carbsTextField = new JTextField();
        constraints.gridx=1;
        constraints.gridy=6;
        mainPanel.add(carbsTextField,constraints);

        JLabel fatsLabel = new JLabel("Fats: ");
        constraints.gridx=0;
        constraints.gridy=7;
        mainPanel.add(fatsLabel,constraints);

        fatsTextField = new JTextField();
        constraints.gridx=1;
        constraints.gridy=7;
        mainPanel.add(fatsTextField,constraints);

        saveButton = new JButton("Save");
        saveButton.addActionListener( e -> {
            if(editingIngredient!=null){
                editingIngredient = buildIngredient();
            } else {
                Ingredient ingredient = buildIngredient();
                if(ingredient==null) return;
                executor.saveIngredient(ingredient);
            }
            this.dispose();
        });
        constraints.gridx=0;
        constraints.gridy=8;
        mainPanel.add(saveButton,constraints);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( e -> this.dispose());
        constraints.gridx=1;
        constraints.gridy=8;
        mainPanel.add(cancelButton,constraints);

        setContentPane(mainPanel);
        pack();
        setSize(new Dimension(677,507));
        setResizable(false);
        setLocationRelativeTo(parent);

    }


    public void createIngredient(){
        setVisible(true);
    }

    public Ingredient editIngredient(Ingredient ingredient){
        editingIngredient = ingredient;
        nameTextField.setText(ingredient.getName());
        priceTextField.setText(String.valueOf(ingredient.getPrice()));
        Nutrients nutrients = ingredient.getNutrients();
        proteinsTextField.setText(String.valueOf(nutrients.getProteins()));
        carbsTextField.setText(String.valueOf(nutrients.getCarbs()));
        fatsTextField.setText(String.valueOf(nutrients.getFats()));
        Quantity quantity = ingredient.getQuantity();
        switch (quantity.getQuantityType()){
            case GRAMS -> quantityTypeBox.setSelectedItem("Grams");
            case PIECES -> quantityTypeBox.setSelectedItem("Pieces");
            case MILLILITERS -> quantityTypeBox.setSelectedItem("Milliliters");
        }
        gramsPerQuantityTextField.setText(String.valueOf(quantity.getGramsPerQuantity()));
        setVisible(true);
        return editingIngredient;
    }

    private Ingredient buildIngredient(){
        /* Checking if values are not illegal */
        String selectedName = nameTextField.getText();
        String selectedPrice = priceTextField.getText();
        String selectedProteins = proteinsTextField.getText();
        String selectedCarbs = carbsTextField.getText();
        String selectedFats = fatsTextField.getText();
        String selectedItem = (String) quantityTypeBox.getSelectedItem();
        String selectedGramsPerQuantity = gramsPerQuantityTextField.getText();

        float price, proteins,carbs,fats;
        float gramsPerQuantity=1;

        try{
            price = Float.parseFloat(selectedPrice);
            proteins = Float.parseFloat(selectedProteins);
            carbs = Float.parseFloat(selectedCarbs);
            fats = Float.parseFloat(selectedFats);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(getParent(),"Incorrect parameters!");
            return null;
        }
        if(selectedName.equals("") || selectedItem == null ){
            JOptionPane.showMessageDialog(getParent(),"Incorrect parameters!");
            return null;
        }


        QuantityType quantityType;
        int quantity = 100;
        switch (selectedItem){
            case "Grams" -> quantityType = QuantityType.GRAMS;
            case "Milliliters" -> quantityType = QuantityType.MILLILITERS;
            case "Pieces" -> {
                quantityType = QuantityType.PIECES;
                quantity = 1;
            }
            default -> throw new IllegalStateException("Unexpected value: " + selectedItem);
        }

        if(!quantityType.equals(QuantityType.GRAMS)){
            try {
                gramsPerQuantity = Float.parseFloat(selectedGramsPerQuantity);
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(getParent(),"Incorrect parameters!");
                return null;
            }
        }


        /* Getting values */
        IngredientBuilder ingredientBuilder = executor.getIngredientBuilder();
        ingredientBuilder.setName(nameTextField.getText());
        ingredientBuilder.setQuantity(new PickyQuantity(quantity,quantityType,gramsPerQuantity));
        ingredientBuilder.setPrice(price);
        NutrientsBuilder nutrientsBuilder = executor.getNutrientsBuilder();
        nutrientsBuilder.setComplexCarbs(carbs);
        nutrientsBuilder.setProteins(proteins);
        nutrientsBuilder.setUnSaturatedFats(fats);
        ingredientBuilder.setNutrients(nutrientsBuilder.build());
        return ingredientBuilder.build();
    }

}
