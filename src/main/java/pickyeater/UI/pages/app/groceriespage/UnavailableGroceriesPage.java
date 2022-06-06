package pickyeater.UI.pages.app.groceriespage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.GroceriesExecutor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UnavailableGroceriesPage extends PickyPage {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JButton generateGroceriesButton;
    private JPanel mainPanel;
    private JLabel txt404;
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
        txt404.setText("");
        try {
            BufferedImage img404 = ImageIO.read(new File("res/images/404.png"));
            txt404.setIcon(new ImageIcon(img404.getScaledInstance(-1, 350, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            System.out.println("Couldn't process image");
        }
        if (groceriesExecutor.isGroceriesGenerated()) {
            PickyPage groceriesPage = new GroceriesPage(groceriesExecutor, parent);
            groceriesPage.showPage();
        } else super.showPage();
    }
}
