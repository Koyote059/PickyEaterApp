package pickyeater.UI.app.mealplanpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.algorithms.MealPlanGenerator;
import pickyeater.algorithms.RandomMealPlanGenerator;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.basics.user.UserStatus;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanCreatorExecutor;
import pickyeater.executors.MealPlanViewerExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MealPlanUnavailablePage extends JFrame {
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
    public MealPlanUnavailablePage() {
        this.mealPlanCreator = ExecutorProvider.getMealPlanExecutor();
        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#B1EA9D"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        setNavigationMenuListeners();

        generateMealPlanButton.addActionListener(e -> {
            setVisible(false);
            new MealPlanGeneratorPage(mealPlanCreator);
        });

        automaticGenerateMealPlanButton.addActionListener(e -> {
            MealPlanGenerator mealPlanGenerator = new RandomMealPlanGenerator();
            User user = mealPlanCreator.getUser();

            MealPlan mealPlan = mealPlanGenerator.generate(mealPlanCreator.getMeals(),
                                user.getUserGoal().getRequiredNutrients(),
                    7,4);


            setVisible(false);
            new MealPlanGeneratorPage(mealPlanCreator,mealPlan);
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
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
    }
}
