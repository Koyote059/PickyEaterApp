package pickyeater.UI.pages.app.mealplanpage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.app.mealplanpage.utils.MealRowPopupMenu;
import pickyeater.UI.pages.choosers.MealInfoJDialog;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanViewerExecutor;
import pickyeater.utils.MouseClickListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
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
    private List<Meal> actualMealsList = null;
    private LocalDate actualDate;

    public MealPlanPage(JFrame parent) {
        super(parent);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        txtBin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/binIcon.png"));
            txtBin.setIcon(new ImageIcon(binImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            txtBin.setText("");
        } catch (IOException | NullPointerException ignored) {
        }
        executor = ExecutorProvider.getMealPlanViewerExecutor();
        actualDate = LocalDate.now();
        dailyMealsTable.getTableHeader().setReorderingAllowed(false);

        dailyMealsTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
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
        dailyMealsTable.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                new MealInfoJDialog(parent, meal).run();
            }
        });
        txtBin.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete it?", "Deleting  " + "groceries", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    executor.deleteMealPlan();
                    PickyPage mealPlanUnavailablePage = new MealPlanUnavailablePage(parent);
                    mealPlanUnavailablePage.showPage();
                }
            }
        });
        dailyMealsTable.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Point framePoint = parent.getLocation();
                    Point realPoint = new Point(point.x - framePoint.x, point.y - framePoint.y);
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
                    popupMenu.show(parent, realPoint.x, realPoint.y);
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
        txtDay.setText(actualDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
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
