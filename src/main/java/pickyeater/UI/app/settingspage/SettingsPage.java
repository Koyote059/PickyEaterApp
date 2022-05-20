package pickyeater.UI.app.settingspage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.registerpage.Register1;
import pickyeater.executors.ExecutorProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPage extends PickyPage {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JLabel txtDeletingZone;
    private JButton btDeleteUser;
    private JButton btResetMeals;
    private JButton btResetIngredients;

    public SettingsPage(JFrame parent) {
        super(parent);
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#B1EA9D"));
        txtDeletingZone.setForeground(Color.red);
        btDeleteUser.setForeground(Color.red);
        btDeleteUser.setBackground(Color.white);
        btResetMeals.setForeground(Color.red);
        btResetMeals.setBackground(Color.white);
        btResetIngredients.setForeground(Color.red);
        btResetIngredients.setBackground(Color.white);
        add(mainPanel);

        btDeleteUser.addActionListener(e -> {
            int x = JOptionPane.showConfirmDialog(SettingsPage.this.mainPanel, "Are you sure? All your info will be lost forever",
                    "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (x == 0){
                ExecutorProvider.getSettingsExecutor().deleteUser();
                setVisible(false);
                new Register1();
            }
        });
        btResetMeals.addActionListener(e -> {
            int x = JOptionPane.showConfirmDialog(SettingsPage.this.mainPanel, "Are you sure? All your added meals will be lost " +
                            "forever", "Reset Meals", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (x == 0){
                ExecutorProvider.getSettingsExecutor().resetMeals();
            }
        });
        btResetIngredients.addActionListener(e -> {
            int x = JOptionPane.showConfirmDialog(SettingsPage.this.mainPanel, "Are you sure? All your added ingredients will be " +
                    "lost forever", "Reset Ingredients", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (x == 0){
                ExecutorProvider.getSettingsExecutor().resetIngredients();
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
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);
    }


}
