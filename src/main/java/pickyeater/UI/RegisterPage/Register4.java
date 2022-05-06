package pickyeater.UI.RegisterPage;

import pickyeater.algorithms.NutrientsRequiremenetCalculatorWrong;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.food.Nutrients;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyNutrientsBuilder;
import pickyeater.executors.RegisterExecutor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register4 extends JFrame {
    private JPanel mainPanel;
    private JButton btBack;
    private JButton btDone;
    private JLabel tfCalories;
    private JTextField tfProteins;
    private JTextField tfCarbs;
    private JTextField tfFats;
    private JButton btReset;
    NutrientsBuilder newNutrientsTmp = new PickyNutrientsBuilder();

    public Register4(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ResetNutirents(registerExecutor);

        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                new Register3(registerExecutor);
            }
        });

        btDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JOptionPane.showMessageDialog(mainPanel, "Selected:"  + "\n" );
                // TODO: FINISH THIS

                // TODO: SAVE TO DATABASE newNutrients

                setVisible(false);
                // TODO: GO TO THE APP WITH new AppPanel(registerExecutor);
            }
        });

        btReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ResetNutirents(registerExecutor);
            }
        });

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Double.parseDouble(tfProteins.getText()) > 5000 | Double.parseDouble(tfProteins.getText()) < 0){
                    JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Proteins", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    newNutrientsTmp.setProteins(Double.parseDouble(tfProteins.getText()));
                    // TODO: Save in database
                }

                if (Double.parseDouble(tfFats.getText()) > 5000 | Double.parseDouble(tfFats.getText()) < 0){
                    JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Fats", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    newNutrientsTmp.setUnSaturatedFats(Double.parseDouble(tfFats.getText()));
                    // TODO: Save in database
                }

                if (Double.parseDouble(tfCarbs.getText()) > 5000 | Double.parseDouble(tfCarbs.getText()) < 0){
                    JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Carbs", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    newNutrientsTmp.setComplexCarbs(Double.parseDouble(tfCarbs.getText()));
                    // TODO: Save in database
                }

                // TODO: Get from database
                tfCalories.setText(Double.toString(newNutrientsTmp.getCalories()));
            }
        };
        tfFats.addActionListener(listener);
        tfCarbs.addActionListener(listener);
        tfProteins.addActionListener(listener);
    }

    private void ResetNutirents(RegisterExecutor registerExecutor){
        // Get nutrients from NitrientsRequirementCalculator
        NutrientsRequirementCalculator nutrientsCalculated = new NutrientsRequiremenetCalculatorWrong();
        Nutrients nutrients = nutrientsCalculated.calculate(registerExecutor.getUserBuilder().getHeight(), registerExecutor.getUserBuilder().getWeight(), registerExecutor.getUserBuilder().getSex(), registerExecutor.getUserBuilder().getLifeStyle());

        // Do stuff to text fields (tf)
        tfCalories.setText(Double.toString(nutrients.getCalories()));
        tfProteins.setText(Double.toString(nutrients.getProteins()));
        tfCarbs.setText(Double.toString(nutrients.getCarbs()));
        tfFats.setText(Double.toString(nutrients.getFats()));

        // Refresh monitor
        setVisible(true);
    }

}
