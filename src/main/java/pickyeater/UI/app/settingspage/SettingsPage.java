package pickyeater.UI.app.settingspage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.registerpage.Register1;
import pickyeater.executors.ExecutorProvider;
import pickyeater.themes.SystemTheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private JComboBox cbTheme;
    private JLabel txtImage;

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

        txtImage.setText("");
        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/binForeverB.png"));
            txtImage.setIcon(new ImageIcon(binImage.getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
        }
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        btDeleteUser.addActionListener(e -> {
            int x = JOptionPane.showConfirmDialog(SettingsPage.this.mainPanel, "Are you sure? All your info will be lost forever",
                    "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (x == 0){
                ExecutorProvider.getSettingsExecutor().deleteUser();
                parent.dispose();
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
        cbTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbTheme.getSelectedIndex() == 0){
                    new SystemTheme().theme1();
                } else if (cbTheme.getSelectedIndex() == 1) {
                    new SystemTheme().theme2();
                } else if (cbTheme.getSelectedIndex() == 2) {
                    new SystemTheme().theme0();
                }
                SwingUtilities.updateComponentTreeUI(MainFrame.getFrames()[0]);
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
