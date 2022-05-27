package pickyeater.UI.pages.app.dailyprogresspage;

import pickyeater.executors.ExecutorProvider;
import pickyeater.utils.StringToNumber;

import javax.swing.*;
import java.awt.*;

public class AddBurntCaloriesPage extends JDialog {
    private JButton btCancel;
    private JTextField tfActivityName;
    private JPanel mainPanel;
    private JButton btSave;
    private JTextField tfBurntCalories;

    public AddBurntCaloriesPage(JFrame parent) {
        super(parent,"Add Burnt Calories",true);

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        btCancel.addActionListener(e -> dispose());
        btSave.addActionListener(e -> {
            int burntCal = (int) new StringToNumber().convertPositiveFloat(tfBurntCalories.getText());
            if (burntCal != 0) {
                if (!tfActivityName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Activity name: " + tfActivityName.getText() + "\n" + "Burnt Calories: " + tfBurntCalories.getText());
                } else {
                    JOptionPane.showMessageDialog(mainPanel,  "Burnt Calories: " + tfBurntCalories.getText());
                }
                ExecutorProvider.getAddBurntCaloriesExecutor().setBurntCalories(burntCal);
                dispose();
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Insert valid number", "", JOptionPane.ERROR_MESSAGE);
            }
        });
        pack(); // TODO -> FIX
        //setResizable(false);
        setLocationRelativeTo(parent);
    }

    public void run() {
        setVisible(true);
    }
}