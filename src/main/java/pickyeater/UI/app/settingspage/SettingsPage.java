package pickyeater.UI.app.settingspage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.registerpage.Register1;
import pickyeater.executors.ExecutorProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPage extends JFrame {
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

    public SettingsPage() {
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#B1EA9D"));

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        txtDeletingZone.setForeground(Color.red);
        btDeleteUser.setForeground(Color.red);
        btDeleteUser.setBackground(Color.white);
        btResetMeals.setForeground(Color.red);
        btResetMeals.setBackground(Color.white);
        btResetIngredients.setForeground(Color.red);
        btResetIngredients.setBackground(Color.white);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                setVisible(false);
                new MainButton(new PanelButtonsConverter(cmd).Convert());
            }
        };
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);
        btDeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = JOptionPane.showConfirmDialog(mainPanel, "Are you sure? All your info will be lost forever",
                        "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (x == 0){
                    ExecutorProvider.getSettingsExecutor().deleteUser();
                    setVisible(false);
                    new Register1();
                }
            }
        });
        btResetMeals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = JOptionPane.showConfirmDialog(mainPanel, "Are you sure? All your added meals will be lost " +
                                "forever", "Reset Meals", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (x == 0){
                    ExecutorProvider.getSettingsExecutor().resetMeals();
                }
            }
        });
        btResetIngredients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = JOptionPane.showConfirmDialog(mainPanel, "Are you sure? All your added ingredients will be " +
                        "lost forever", "Reset Ingredients", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (x == 0){
                    ExecutorProvider.getSettingsExecutor().resetIngredients();
                }
            }
        });
    }
}
