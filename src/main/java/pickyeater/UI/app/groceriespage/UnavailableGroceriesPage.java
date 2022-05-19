package pickyeater.UI.app.groceriespage;

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.GroceriesExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.concurrent.Executor;

public class UnavailableGroceriesPage extends JFrame {
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton generateGroceriesButton;
    private JPanel mainPanel;

    private GroceriesExecutor groceriesExecutor = ExecutorProvider.getGroceriesExecutor();


    public UnavailableGroceriesPage() {
        if(groceriesExecutor.isGroceriesGenerated()){
            new GroceriesPage(groceriesExecutor);
            return;
        }
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#B1EA9D"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setNavigationMenuListeners();
        generateGroceriesButton.addActionListener( e -> {
            if(groceriesExecutor.isGroceriesAvailable()){
                setVisible(false);
                new GroceriesPage(groceriesExecutor);
            } else {
                JOptionPane.showMessageDialog(this,"Unavailable Meal Plan!\n You need to create a Meal Plan in order to generate groceries...");
            }
        });


    }

    private void setNavigationMenuListeners(){
        ActionListener listener = e -> {
            String cmd = e.getActionCommand();
            setVisible(false);
            new MainButton(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btDiet.addActionListener(listener);
        btFood.addActionListener(listener);
    }

}
