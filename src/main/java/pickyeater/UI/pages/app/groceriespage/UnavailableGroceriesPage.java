package pickyeater.UI.pages.app.groceriespage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.GroceriesExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UnavailableGroceriesPage extends PickyPage {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JButton generateGroceriesButton;
    private JPanel mainPanel;
    private final GroceriesExecutor groceriesExecutor = ExecutorProvider.getGroceriesExecutor();

    public UnavailableGroceriesPage(JFrame parent) {
        super(parent);
        ColorButtons.ColorLeftButtons(btGroceries, btDailyProgress, btSettings, btDiet, btUser);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        setNavigationMenuListeners();
        generateGroceriesButton.addActionListener(e -> {
            if (groceriesExecutor.isGroceriesAvailable()) {
                groceriesExecutor.generateGroceries();
                PickyPage groceriesPage = new GroceriesPage(groceriesExecutor, parent);
                groceriesPage.showPage();
            } else {
                JOptionPane.showMessageDialog(this, "Unavailable Meal Plan!\n You need to create a Meal Plan in order to generate groceries...");
            }
        });
    }

    private void setNavigationMenuListeners() {
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            MainFrame.changePage(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btDiet.addActionListener(listener);
    }

    @Override
    public void showPage() {
        if (groceriesExecutor.isGroceriesGenerated()) {
            PickyPage groceriesPage = new GroceriesPage(groceriesExecutor, parent);
            groceriesPage.showPage();
        } else
            super.showPage();
    }
}
