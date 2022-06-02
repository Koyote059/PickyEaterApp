package pickyeater.UI.pages.app.mealplanpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.algorithms.MealPlanGenerator;
import pickyeater.algorithms.RandomMealPlanGenerator;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanCreatorExecutor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MealPlanUnavailablePage extends PickyPage {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JButton btCreateMealPlan;
    private JButton btAutomaticGenerateMealPlan;
    private JLabel txt404;
    private final MealPlanCreatorExecutor mealPlanCreator;

    public MealPlanUnavailablePage(JFrame parent) {
        super(parent);
        showImage();
        this.mealPlanCreator = ExecutorProvider.getMealPlanExecutor();
        ColorButtons.ColorLeftButtons(btDiet, btDailyProgress, btSettings, btGroceries, btUser);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        setNavigationMenuListeners();
        btCreateMealPlan.addActionListener(e -> {
            if (mealPlanCreator.getMeals().size() < 5) {
                JOptionPane.showMessageDialog(this, "Unable to create a meal plan.\n Insufficient meals!");
                return;
            }
            PickyPage mealPlanGeneratorPage = new MealPlanGeneratorPage(mealPlanCreator, parent);
            mealPlanGeneratorPage.showPage();
        });
        btAutomaticGenerateMealPlan.addActionListener(e -> {
            if (mealPlanCreator.getMeals().size() < 5) {
                JOptionPane.showMessageDialog(this, "Unable to create a meal plan.\n Insufficient meals!");
                return;
            }
            MealPlanGenerator mealPlanGenerator = new RandomMealPlanGenerator();
            User user = mealPlanCreator.getUser();
            MealPlan mealPlan = mealPlanGenerator.generate(mealPlanCreator.getMeals(), user.getUserGoal().getRequiredNutrients(), 7, 4);
            PickyPage mealPlanGeneratorPage = new MealPlanGeneratorPage(mealPlanCreator, mealPlan, parent);
            mealPlanGeneratorPage.showPage();
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
        btGroceries.addActionListener(listener);
    }
    private void showImage(){
        txt404.setText("");
        try {
            BufferedImage img404 = ImageIO.read(new File("res/images/404.png"));
            txt404.setIcon(new ImageIcon(img404.getScaledInstance(-1, 350, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            System.out.println("Couldn't process image");
        }
    }
}
