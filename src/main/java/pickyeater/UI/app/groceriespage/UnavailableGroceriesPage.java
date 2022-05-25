package pickyeater.UI.app.groceriespage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
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

    private GroceriesExecutor groceriesExecutor = ExecutorProvider.getGroceriesExecutor();


    public UnavailableGroceriesPage(JFrame parent) {
        super(parent);

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);

        if(groceriesExecutor.isGroceriesAvailable()){
            PickyPage groceriesPage = new GroceriesPage(groceriesExecutor,parent);
            groceriesPage.showPage();
        }
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#B1EA9D"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));
        setNavigationMenuListeners();
        generateGroceriesButton.addActionListener( e -> {
            if(groceriesExecutor.isGroceriesAvailable()){
                PickyPage groceriesPage = new GroceriesPage(groceriesExecutor,parent);
                groceriesPage.showPage();
            } else {
                JOptionPane.showMessageDialog(this,"Unavailable Meal Plan!\n You need to create a Meal Plan in order to generate groceries...");
            }
        });


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
        btDiet.addActionListener(listener);
    }

    @Override
    public void showPage() {
        if(groceriesExecutor.isGroceriesAvailable()) {
            PickyPage groceriesPage = new GroceriesPage(groceriesExecutor, parent);
            groceriesPage.showPage();
        } else super.showPage();
    }
}
