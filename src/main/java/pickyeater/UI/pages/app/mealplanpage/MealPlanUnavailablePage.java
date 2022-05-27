package pickyeater.UI.pages.app.mealplanpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.algorithms.MealPlanGenerator;
import pickyeater.algorithms.RandomMealPlanGenerator;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanCreatorExecutor;
import pickyeater.UI.themes.ColorButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MealPlanUnavailablePage extends PickyPage {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JButton generateMealPlanButton;
    private JButton automaticGenerateMealPlanButton;
    private MealPlanCreatorExecutor mealPlanCreator;
    public MealPlanUnavailablePage(JFrame parent) {
        super(parent);
        this.mealPlanCreator = ExecutorProvider.getMealPlanExecutor();
        new ColorButtons().ColorLeftButtons(btDiet, btDailyProgress, btSettings, btGroceries, btUser);

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        setNavigationMenuListeners();

        generateMealPlanButton.addActionListener(e -> {
            if(mealPlanCreator.getMeals().size()<5){
                JOptionPane.showMessageDialog(this,"Unable to create a meal plan.\n Insufficient meals!");
                return;
            }
            PickyPage mealPlanGeneratorPage = new MealPlanGeneratorPage(mealPlanCreator,parent);
            mealPlanGeneratorPage.showPage();
        });

        automaticGenerateMealPlanButton.addActionListener(e -> {
            if(mealPlanCreator.getMeals().size()<5){
                JOptionPane.showMessageDialog(this,"Unable to create a meal plan.\n Insufficient meals!");
                return;
            }
            MealPlanGenerator mealPlanGenerator = new RandomMealPlanGenerator();
            User user = mealPlanCreator.getUser();

            MealPlan mealPlan = mealPlanGenerator.generate(mealPlanCreator.getMeals(),
                                user.getUserGoal().getRequiredNutrients(),
                    7,4);
            PickyPage mealPlanGeneratorPage = new MealPlanGeneratorPage(mealPlanCreator,mealPlan,parent);
            mealPlanGeneratorPage.showPage();
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
        btGroceries.addActionListener(listener);
    }
}
