package pickyeater.UI.app.mealplanpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.app.MainPanel;
import pickyeater.UI.choosers.MealInfoJDialog;
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
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

public class MealPlanPage extends JPanel {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JButton previousButton;
    private JLabel dayLabel;
    private JButton nextButton;
    private JButton todaysButton;
    private JTable dailyMealsTable;
    private JLabel binLabel;
    private final MealPlanViewerExecutor executor;

    private LocalDate actualDate = null;
    public MealPlanPage(JFrame parent) {

        CardLayout layout = new CardLayout();
        setLayout(layout);
        add(mainPanel,"MainPanel");
        add(new MealPlanUnavailablePage(parent, this),"MealPlanUnavailablePage");

        binLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/binIcon.png"));
            binLabel.setIcon(new ImageIcon(binImage.getScaledInstance(20,20,Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {

        }

        MealPlanPage panel = this;
        binLabel.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(binLabel,"Are you sure you want to delete it?","Deleting  groceries",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    executor.deleteMealPlan();

                    layout.show(panel,"MealPlanUnavailablePage");
                }
            }
        });

        executor = ExecutorProvider.getMealPlanViewerExecutor();

        actualDate = LocalDate.now();

        dailyMealsTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        DefaultTableModel tableModel = (DefaultTableModel) dailyMealsTable.getModel();
        tableModel.addColumn("Meals");
        tableModel.addColumn("Quantity");

        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#B1EA9D"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#FFFFFF"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        setButtonsListeners(parent);
        todaysButton.doClick();
        dailyMealsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dailyMealsTable.removeEditor();
        setNavigationMenuListeners();
        if(!executor.isMealPlanAvailable()){
            layout.show(this,"MealPlanUnavailablePage");
        } else  {
            layout.show(this,"MainPanel");
        }
    }

    private void setUpContent(DailyMealPlan dailyMealPlan){
        dayLabel.setText(actualDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
        List<Meal> meals = dailyMealPlan.getMeals();
        DefaultTableModel tableModel = (DefaultTableModel) dailyMealsTable.getModel();
        tableModel.setRowCount(0);
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(2);
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            Object[] row = new Object[]{
                    meal.getName(),
                    formatter.format(meal.getWeight()) + " g"
            };
            tableModel.addRow(row);
        }
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
    }

    private void setButtonsListeners(JFrame parent) {
        todaysButton.addActionListener( e -> {
            actualDate = LocalDate.now();
            Optional<DailyMealPlan> dailyMealPlanOptional = executor.getDailyMealPlan(actualDate);
            if(dailyMealPlanOptional.isEmpty()){
                return;
            }
            setUpContent(dailyMealPlanOptional.get());
        });
        nextButton.addActionListener( e -> {
            actualDate = actualDate.plusDays(1);
            Optional<DailyMealPlan> dailyMealPlanOptional = executor.getDailyMealPlan(actualDate);
            if(dailyMealPlanOptional.isEmpty()){
                actualDate = actualDate.minusDays(1);
                return;
            }
            setUpContent(dailyMealPlanOptional.get());
        });
        previousButton.addActionListener( e -> {
            actualDate = actualDate.minusDays(1);
            Optional<DailyMealPlan> dailyMealPlanOptional = executor.getDailyMealPlan(actualDate);
            if(dailyMealPlanOptional.isEmpty()){
                actualDate = actualDate.plusDays(1);
                return;
            }
            setUpContent(dailyMealPlanOptional.get());
        });

        dailyMealsTable.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()<2) return;
                int selectedIndex = dailyMealsTable.getSelectedRow();
                if(selectedIndex<0) return;
                Optional<DailyMealPlan> dailyMealPlanOptional = executor.getDailyMealPlan(actualDate);
                if(dailyMealPlanOptional.isEmpty()) throw new RuntimeException("Error i dailyMealPlan Database: missing value." );
                DailyMealPlan dailyMealPlan = dailyMealPlanOptional.get();
                Meal meal = dailyMealPlan.getMeals().get(selectedIndex);
                new MealInfoJDialog(parent,meal).run();
            }
        });
    }

}
