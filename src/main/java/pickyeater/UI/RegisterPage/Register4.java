package pickyeater.UI.RegisterPage;

import pickyeater.algorithms.NutrientsRequiremenetCalculatorWrong;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.food.Nutrients;
import pickyeater.executors.RegisterExecutor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register4 extends JFrame {
    private JPanel mainPanel;
    private JButton btBack;
    private JButton btDone;
    private JTextField tfCalories;
    private JTextField tfProteins;
    private JTextField tfCarbs;
    private JTextField tfFats;
    private JButton btReset;

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
