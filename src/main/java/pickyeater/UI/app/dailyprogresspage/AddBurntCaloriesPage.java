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

public class AddBurntCaloriesPage extends PickyPage {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton btCancel;
    private JTextField tfActivityName;
    private JPanel mainPanel;
    private JButton btSave;
    private JTextField tfBurntCalories;

    public AddBurntCaloriesPage(JFrame parent) {

        super(parent);
        btDailyProgress.setBackground(Color.decode("#B1EA9D"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));


        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        btCancel.addActionListener(e -> MainFrame.changePage(PanelButtons.PROGRESS));
        btSave.addActionListener(e -> {
            float burntCal = new StringToNumber().convertPositiveFloat(tfBurntCalories.getText());

            if (burntCal != 0) {
                if (!tfActivityName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Activity name: " + tfActivityName.getText() + "\n" + "Burnt Calories: " + tfBurntCalories.getText());
                } else {
                    JOptionPane.showMessageDialog(mainPanel,  "Burnt Calories: " + tfBurntCalories.getText());
                }
                ExecutorProvider.getAddBurntCaloriesExecutor().setBurntCalories(burntCal);
                MainFrame.changePage(PanelButtons.PROGRESS);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Insert valid number", "", JOptionPane.ERROR_MESSAGE);
            }
        });
        setNavigationMenuListeners();
    }

    private void setNavigationMenuListeners(){
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            MainFrame.changePage(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);
    }

}