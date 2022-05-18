package pickyeater.UI.registerpage;

/**
 * @author Claudio Di Maio
 */
import pickyeater.utils.AgeCalculator;
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.algorithms.HarrisBenedictCalculator;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.food.Nutrients;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyNutrientsBuilder;
import pickyeater.builders.UserBuilder;
import pickyeater.executors.user.RegisterExecutor;
import pickyeater.utils.StringToNumber;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register4 extends JFrame {
    private JPanel mainPanel;
    private JButton btBack;
    private JButton btDone;
    private JLabel txtCalories;
    private JTextField tfProteins;
    private JTextField tfCarbs;
    private JTextField tfFats;
    private JButton btReset;
    NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();

    public Register4(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        UserBuilder userBuilder = registerExecutor.getUserBuilder();

        resetNutrients(userBuilder);

        StringToNumber stn = new StringToNumber();

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

                JOptionPane.showMessageDialog(mainPanel, "Selected:"  + "\n" + "Calories: " + nutrientsBuilder.getCalories() + "\n" + "Proteins: " + nutrientsBuilder.getProteins() + "\n" + "Carbs: " + nutrientsBuilder.getCarbs() + "\n" + "Fats: " + nutrientsBuilder.getFats());

                userBuilder.setRequiredNutrients(nutrientsBuilder.build());
                registerExecutor.saveUser(userBuilder.build());

                setVisible(false);
                new MainButton(PanelButtons.PROGRESS);
            }
        });

        btReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetNutrients(userBuilder);
            }
        });

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (stn.convertPositiveDouble(tfProteins.getText()) > 5000 | stn.convertPositiveDouble(tfProteins.getText()) < 0){
                    JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Proteins", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    nutrientsBuilder.setProteins(stn.convertPositiveFloat(tfProteins.getText()));
                }

                if (stn.convertPositiveDouble(tfFats.getText()) > 5000 | stn.convertPositiveDouble(tfFats.getText()) < 0){
                    JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Fats", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    nutrientsBuilder.setUnSaturatedFats(stn.convertPositiveFloat(tfFats.getText()));
                }

                if (stn.convertPositiveFloat(tfCarbs.getText()) > 5000 | stn.convertPositiveFloat(tfCarbs.getText()) < 0){
                    JOptionPane.showMessageDialog(mainPanel, "Insert valid number in Carbs", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    nutrientsBuilder.setComplexCarbs(stn.convertPositiveFloat(tfCarbs.getText()));
                }

                txtCalories.setText(Double.toString(nutrientsBuilder.getCalories()));
            }
        };
        tfFats.addActionListener(listener);
        tfCarbs.addActionListener(listener);
        tfProteins.addActionListener(listener);
    }

    private void resetNutrients(UserBuilder userBuilder){
        NutrientsRequirementCalculator nutrientsCalculated = new HarrisBenedictCalculator();
        Nutrients nutrients = nutrientsCalculated.calculate(userBuilder.getHeight(),
                userBuilder.getWeight(), new AgeCalculator().age(userBuilder.getDateOfBirth()),
                userBuilder.getSex(), userBuilder.getLifeStyle(),userBuilder.getWeightVariationGoal());

        txtCalories.setText(Double.toString(nutrients.getCalories()));
        tfProteins.setText(Double.toString(nutrients.getProteins()));
        tfCarbs.setText(Double.toString(nutrients.getCarbs()));
        tfFats.setText(Double.toString(nutrients.getFats()));

        // save also to newNutrientsTmp
        nutrientsBuilder.setComplexCarbs(new StringToNumber().convertPositiveFloat(tfCarbs.getText()));
        nutrientsBuilder.setUnSaturatedFats(new StringToNumber().convertPositiveFloat(tfFats.getText()));
        nutrientsBuilder.setProteins(new StringToNumber().convertPositiveFloat(tfProteins.getText()));
    }

}
