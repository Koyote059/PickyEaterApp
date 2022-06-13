package pickyeater.UI.pages.app.mealplanpage;

import pickyeater.utils.MealPlanGeneratorBundle;
import pickyeater.utils.StringToNumber;

import javax.swing.*;

public class MealPlanGeneratorEditor extends JDialog {
    private JPanel contentPane;
    private JButton cancelButton;
    private JButton doneButton;
    private JTextField daysTextField;
    private JTextField mealsInADayTextField;

    public MealPlanGeneratorEditor(JFrame parent, MealPlanGeneratorBundle bundle, int mealsSize) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Generator settings");
        getRootPane().setDefaultButton(cancelButton);
        cancelButton.addActionListener(l -> dispose());
        daysTextField.setText(String.valueOf(bundle.getDays()));
        mealsInADayTextField.setText(String.valueOf(bundle.getMealsInADay()));
        doneButton.addActionListener(l -> {
            try {
                int days = (int) StringToNumber.convertPositiveFloatException(daysTextField.getText());
                int mealsInADay = (int) StringToNumber.convertPositiveFloatException(mealsInADayTextField.getText());
                if (days > 20 || mealsInADay > 20)
                    throw new NumberFormatException();
                if (mealsSize < mealsInADay) {
                    JOptionPane.showMessageDialog(parent, "Insufficient meals in database!\n");
                    return;
                }
                bundle.setDays(days);
                bundle.setMealsInADay(mealsInADay);
                dispose();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Invalid values!");
            }
        });
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public void display() {
        setVisible(true);
    }
}
