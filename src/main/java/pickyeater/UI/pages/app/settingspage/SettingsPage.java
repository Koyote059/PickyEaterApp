package pickyeater.UI.pages.app.settingspage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.choosers.IngredientChooser;
import pickyeater.UI.pages.choosers.MealsChooser;
import pickyeater.UI.pages.leftbuttons.PanelButtons;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.pages.registerpage.Register1;
import pickyeater.UI.pages.registerpage.RegisterMainFrame;
import pickyeater.executors.ExecutorProvider;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    private JButton btDiet;
    private JLabel txtDeletingZone;
    private JButton btDeleteUser;
    private JButton btResetMeals;
    private JButton btResetIngredients;
    private JComboBox cbTheme;
    private JLabel txtImage;
    private JButton manageMealsButton;
    private JButton manageIngredientsButton;

    public SettingsPage(JFrame parent) {
        super(parent);
        new ColorButtons().ColorLeftButtons(btSettings, btDailyProgress, btDiet, btGroceries, btUser);
        ThemesEnum te = new ThemeHandler().ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            cbTheme.setSelectedIndex(0);
        } else if (te == ThemesEnum.DARK_THEME) {
            cbTheme.setSelectedIndex(1);
        } else if (te == ThemesEnum.GREEN_THEME) {
            cbTheme.setSelectedIndex(2);
        }
        txtDeletingZone.setForeground(Color.red);
        btDeleteUser.setForeground(Color.red);
        btResetMeals.setForeground(Color.red);
        btResetIngredients.setForeground(Color.red);

        txtImage.setText("");
        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/binIconR.png"));
            txtImage.setIcon(new ImageIcon(binImage.getScaledInstance(30,30, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
        }
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        btDeleteUser.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(SettingsPage.this.mainPanel, "Are you sure? All your info will be lost forever",
                    "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION){
                ExecutorProvider.getSettingsExecutor().deleteUser();
                ExecutorProvider.getSettingsExecutor().deleteGroceries();
                parent.dispose();
                new RegisterMainFrame();
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

        cbTheme.addActionListener(e -> {
            if (cbTheme.getSelectedIndex() == 0){
                ExecutorProvider.getSettingsExecutor().changeTheme(ThemesEnum.LIGHT_THEME);
            } else if (cbTheme.getSelectedIndex() == 1) {
                ExecutorProvider.getSettingsExecutor().changeTheme(ThemesEnum.DARK_THEME);
            } else if (cbTheme.getSelectedIndex() == 2) {
                ExecutorProvider.getSettingsExecutor().changeTheme(ThemesEnum.GREEN_THEME);
            }

            new MainFrame();
            MainFrame.changePage(PanelButtons.SETTINGS);
            parent.dispose();
        });

        manageMealsButton.addActionListener( l -> {
            MealsChooser chooser = new MealsChooser(parent);
            chooser.manageMeals();
        });

        manageIngredientsButton.addActionListener( l -> {
            IngredientChooser chooser = new IngredientChooser(parent);
            chooser.manageIngredients();
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
        btDiet.addActionListener(listener);
    }
}
