package pickyeater.UI.app.mealplanpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.app.MainPanel;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.algorithms.MealPlanGenerator;
import pickyeater.algorithms.RandomMealPlanGenerator;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanCreatorExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MealPlanUnavailablePage extends JPanel {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton generateMealPlanButton;
    private JButton automaticGenerateMealPlanButton;
    private MealPlanCreatorExecutor mealPlanCreator;
    public MealPlanUnavailablePage(JFrame parent, JPanel mealPlanPage) {
        this.mealPlanCreator = ExecutorProvider.getMealPlanExecutor();
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#B1EA9D"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        add(mainPanel);
        setNavigationMenuListeners();

        generateMealPlanButton.addActionListener(e -> {
            setVisible(false);
            mealPlanPage.add(new MealPlanGeneratorPage(mealPlanCreator,mealPlanPage),"MealPlanGeneratorPage");
            CardLayout layout = (CardLayout) mealPlanPage.getLayout();
            layout.show(mealPlanPage,"MealPlanGeneratorPage");
        });

        automaticGenerateMealPlanButton.addActionListener(e -> {
            MealPlanGenerator mealPlanGenerator = new RandomMealPlanGenerator();
            User user = mealPlanCreator.getUser();

            MealPlan mealPlan = mealPlanGenerator.generate(mealPlanCreator.getMeals(),
                                user.getUserGoal().getRequiredNutrients(),
                    7,4);

            mealPlanPage.add(new MealPlanGeneratorPage(mealPlanCreator,mealPlan,mealPlanPage),"MealPlanGeneratorPage");
            CardLayout layout = (CardLayout) mealPlanPage.getLayout();
            layout.show(mealPlanPage,"MealPlanGeneratorPage");

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
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);
    }
}
