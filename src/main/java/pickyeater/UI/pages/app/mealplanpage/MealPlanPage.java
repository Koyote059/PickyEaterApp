package pickyeater.UI.pages.app.mealplanpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.choosers.MealInfoJDialog;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.builders.MealPlanBuilder;
import pickyeater.builders.PickyMealPlanBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanViewerExecutor;
import pickyeater.utils.Resources;
import pickyeater.utils.pagesutils.MealRowPopupMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class MealPlanPage extends PickyPage {
    private final MealPlanViewerExecutor executor;
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JButton previousButton;
    private JLabel txtDay;
    private JButton nextButton;
    private JButton todaysButton;
    private JTable dailyMealsTable;
    private JLabel txtBin;
    private JLabel txtEdit;
    private List<Meal> actualMealsList = null;
    private LocalDate actualDate;

    public MealPlanPage(JFrame parent) {
        super(parent);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        executor = ExecutorProvider.getMealPlanViewerExecutor();
        txtBin.setToolTipText("Click to delete the meal plan");
        txtBin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage binImage = ImageIO.read(new File(Resources.getBinIcon()));
            txtBin.setIcon(new ImageIcon(binImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            txtBin.setText("");
        } catch (IOException | NullPointerException ignored) {
            txtBin.setText("X");
        }
        txtBin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int result = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete it?", "Deleting  " + "groceries", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    executor.deleteMealPlan();
                    PickyPage mealPlanUnavailablePage = new MealPlanUnavailablePage(parent);
                    mealPlanUnavailablePage.showPage();
                }
            }
        });
        txtEdit.setToolTipText("Click to edit the meal plan");
        txtEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage binImage = ImageIO.read(new File(Resources.getPencilPic()));
            txtEdit.setIcon(new ImageIcon(binImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            txtEdit.setText("");
        } catch (IOException | NullPointerException ignored) {
            txtEdit.setText("E");
        }
        txtEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MealPlan actualMealPlan = executor.getMealPlan().get();
                MealPlanBuilder mealPlanBuilder = new PickyMealPlanBuilder();
                LocalDate date = LocalDate.now();
                for (int i = 0; i < actualMealPlan.getDailyMealPlans().size(); i++) {
                    DailyMealPlan dailyMealPlan = executor.getDailyMealPlan(date).get();
                    mealPlanBuilder.addDailyMealPlan(dailyMealPlan);
                    date = date.plusDays(1);
                }
                PickyPage mealPlanGeneratorPage = new MealPlanGeneratorPage(mealPlanBuilder.build(), parent);
                mealPlanGeneratorPage.showPage();
            }
        });
        actualDate = LocalDate.now();
        dailyMealsTable.getTableHeader().setReorderingAllowed(false);
        dailyMealsTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        dailyMealsTable.setToolTipText("Click to add to eaten meals");
        DefaultTableModel tableModel = (DefaultTableModel) dailyMealsTable.getModel();
        tableModel.addColumn("Meals");
        tableModel.addColumn("Quantity");
        ColorButtons.ColorLeftButtons(btDiet, btDailyProgress, btSettings, btGroceries, btUser);
        setButtonsListeners(parent);
        dailyMealsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dailyMealsTable.removeEditor();
        setNavigationMenuListeners();
    }

    private void setButtonsListeners(JFrame parent) {
        todaysButton.addActionListener(e -> {
            actualDate = LocalDate.now();
            Optional<DailyMealPlan> dailyMealPlanOptional = executor.getDailyMealPlan(actualDate);
            if (dailyMealPlanOptional.isEmpty()) {
                return;
            }
            setUpContent(dailyMealPlanOptional.get());
        });
        nextButton.addActionListener(e -> {
            actualDate = actualDate.plusDays(1);
            Optional<DailyMealPlan> dailyMealPlanOptional = executor.getDailyMealPlan(actualDate);
            if (dailyMealPlanOptional.isEmpty()) {
                actualDate = actualDate.minusDays(1);
                return;
            }
            setUpContent(dailyMealPlanOptional.get());
        });
        previousButton.addActionListener(e -> {
            actualDate = actualDate.minusDays(1);
            Optional<DailyMealPlan> dailyMealPlanOptional = executor.getDailyMealPlan(actualDate);
            if (dailyMealPlanOptional.isEmpty()) {
                actualDate = actualDate.plusDays(1);
                return;
            }
            setUpContent(dailyMealPlanOptional.get());
        });
        dailyMealsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() < 2)
                    return;
                int selectedIndex = dailyMealsTable.getSelectedRow();
                if (selectedIndex < 0)
                    return;
                Optional<DailyMealPlan> dailyMealPlanOptional = executor.getDailyMealPlan(actualDate);
                if (dailyMealPlanOptional.isEmpty())
                    throw new RuntimeException("Error i dailyMealPlan Database: missing value.");
                DailyMealPlan dailyMealPlan = dailyMealPlanOptional.get();
                Meal meal = dailyMealPlan.getMeals().get(selectedIndex);
                new MealInfoJDialog(parent, meal).setVisible(true);
            }
        });
        dailyMealsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    int selectedIndex = dailyMealsTable.rowAtPoint(e.getPoint());
                    if (selectedIndex < 0)
                        return;
                    dailyMealsTable.setRowSelectionInterval(selectedIndex, selectedIndex);
                    Meal selectedMeal = actualMealsList.get(selectedIndex);
                    MealRowPopupMenu popupMenu = new MealRowPopupMenu();
                    popupMenu.addEatenMealsItemListener(l -> {
                        executor.addToEatenMeals(selectedMeal);
                        JOptionPane.showMessageDialog(parent, "Added to eaten meals!");
                    });
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
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
        btGroceries.addActionListener(listener);
    }

    private void setUpContent(DailyMealPlan dailyMealPlan) {
        txtDay.setText(actualDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        actualMealsList = dailyMealPlan.getMeals();
        DefaultTableModel tableModel = (DefaultTableModel) dailyMealsTable.getModel();
        tableModel.setRowCount(0);
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(2);
        for (Meal meal : actualMealsList) {
            Object[] row = new Object[]{meal.getName(), formatter.format(meal.getWeight()) + " g"};
            tableModel.addRow(row);
        }
    }

    @Override
    public void showPage() {
        if (!executor.isMealPlanAvailable()) {
            PickyPage mealPlanUnavailablePage = new MealPlanUnavailablePage(parent);
            mealPlanUnavailablePage.showPage();
            return;
        }
        todaysButton.doClick();
        super.showPage();
    }
}
