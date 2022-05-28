package pickyeater.UI.pages.registerpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.app.dailyprogresspage.DailyProgressPage;
import pickyeater.UI.pages.app.groceriespage.UnavailableGroceriesPage;
import pickyeater.UI.pages.app.mealplanpage.MealPlanPage;
import pickyeater.UI.pages.app.settingspage.SettingsPage;
import pickyeater.UI.pages.app.userpage.UserPage;
import pickyeater.UI.pages.leftbuttons.PanelButtons;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.user.RegisterExecutor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RegisterMainFrame extends JFrame {

    private final Container container;
    private static RegisterMainFrame instance;

    private static final Map<String, PickyPage> pages = new HashMap<>();

    public RegisterMainFrame(){
        container = getContentPane();
        CardLayout layout = new CardLayout();
        container.setLayout(layout);
        RegisterExecutor registerExecutor = ExecutorProvider.getRegisterExecutor();
        pages.put(Register1.class.getName(),new Register1(registerExecutor,this));
        pages.put(Register2.class.getName(),new Register2(registerExecutor,this));
        pages.put(Register3.class.getName(),new Register3(registerExecutor,this));
        pages.put(Register4.class.getName(),new Register4(registerExecutor,this));

        setVisible(true);
        instance = this;

        setSize(677, 507);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 677/2,
                Toolkit.getDefaultToolkit().getScreenSize().height/2 - 507/2);
        changePage(1);
    }

    public static void changePage(int index){
        switch (index) {
            case 1 -> pages.get(Register1.class.getName()).showPage();
            case 2 -> pages.get(Register2.class.getName()).showPage();
            case 3 -> pages.get(Register3.class.getName()).showPage();
            case 4 -> pages.get(Register4.class.getName()).showPage();
            default -> System.out.println("ERROR: " + index);
        }
    }
}
