package pickyeater.UI.app.groceriespage;

import pickyeater.UI.app.MainPanel;
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.GroceriesExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UnavailableGroceriesPage extends JPanel {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton generateGroceriesButton;
    private JPanel mainPanel;

    private GroceriesExecutor groceriesExecutor = ExecutorProvider.getGroceriesExecutor();


    public UnavailableGroceriesPage(JFrame parent) {
        if(groceriesExecutor.isGroceriesGenerated()){
            new GroceriesPage(groceriesExecutor,parent);
            return;
        }
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#B1EA9D"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));
        add(mainPanel);
        setNavigationMenuListeners();
        generateGroceriesButton.addActionListener( e -> {
            if(groceriesExecutor.isGroceriesAvailable()){
                setVisible(false);
                new GroceriesPage(groceriesExecutor,parent);
            } else {
                JOptionPane.showMessageDialog(this,"Unavailable Meal Plan!\n You need to create a Meal Plan in order to generate groceries...");
            }
        });


    }

    private void setNavigationMenuListeners(){
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            MainPanel.changePage(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btDiet.addActionListener(listener);
        btFood.addActionListener(listener);
    }

}
