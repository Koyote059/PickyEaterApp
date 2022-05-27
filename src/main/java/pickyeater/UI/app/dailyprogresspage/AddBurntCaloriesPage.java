package pickyeater.UI.app.dailyprogresspage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.executors.ExecutorProvider;
import pickyeater.utils.StringToNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void run() {
        setVisible(true);
    }
}