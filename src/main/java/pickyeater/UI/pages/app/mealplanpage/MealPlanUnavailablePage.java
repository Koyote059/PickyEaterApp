package pickyeater.UI.pages.app.mealplanpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.algorithms.GreedyMealPlanGenerator;
import pickyeater.algorithms.MealPlanGenerator;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.basics.user.User;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanCreatorExecutor;
import pickyeater.utils.MealPlanGeneratorBundle;
import pickyeater.utils.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MealPlanUnavailablePage extends PickyPage {
    private final MealPlanCreatorExecutor mealPlanCreator;
    private final MealPlanGeneratorBundle bundle;
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JButton btCreateMealPlan;
    private JButton btAutomaticGenerateMealPlan;
    private JLabel txt404;
    private JPanel buttonsPanel;

    public MealPlanUnavailablePage(JFrame parent) {
        super(parent);
        showImage();
        this.mealPlanCreator = ExecutorProvider.getMealPlanExecutor();
        bundle = mealPlanCreator.getBundle();
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
            MealPlanGenerator mealPlanGenerator = new GreedyMealPlanGenerator();
            User user = mealPlanCreator.getUser();
            MealPlan mealPlan = mealPlanGenerator.generate(mealPlanCreator.getMeals(), user.getUserGoal().getRequiredNutrients(), bundle.getDays(), bundle.getMealsInADay());
            PickyPage mealPlanGeneratorPage = new MealPlanGeneratorPage(mealPlanCreator, mealPlan, parent);
            mealPlanGeneratorPage.showPage();
        });
        btAutomaticGenerateMealPlan.setToolTipText("Right click to edit settings");
        btAutomaticGenerateMealPlan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPopupMenu menu = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("Edit settings");
                menu.add(menuItem);
                menuItem.addActionListener(listener -> {
                    MealPlanGeneratorEditor page = new MealPlanGeneratorEditor(parent, bundle, mealPlanCreator.getMeals().size());
                    page.display();
                    mealPlanCreator.saveBundle(bundle);
                });
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    private void showImage() {
        txt404.setText("");
        try {
            BufferedImage img404 = ImageIO.read(new File(Resources.get404Pic()));
            txt404.setIcon(new ImageIcon(img404.getScaledInstance(-1, 350, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            System.out.println("Couldn't process image");
        }
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

    private void createUIComponents() {
        buttonsPanel = new JPanel(new GridLayout(1, 2));
        btCreateMealPlan = new JButton("Create Meal Plan");
        btAutomaticGenerateMealPlan = new JButton("Generate Meal Plan");
        btAutomaticGenerateMealPlan.setToolTipText("Right click for settings");
        buttonsPanel.add(btCreateMealPlan);
        buttonsPanel.add(btAutomaticGenerateMealPlan);
    }
}
