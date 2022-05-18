package pickyeater.UI.app.mealplanpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.app.mealplanpage.utils.DailyMealPlanColumn;
import pickyeater.UI.choosers.MealsChooser;
import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.builders.MealPlanBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealPlanCreatorExecutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealPlanGeneratorPage extends JFrame {
    private JPanel mainPanel;
    private JButton doneButton;
    private JButton cancelButton;
    private JPanel topPanel;
    private MealPlanCreatorExecutor mealPlanCreator;
    private MealPlanBuilder mealPlanBuilder;

    List<DailyMealPlanColumn> columns = new ArrayList<>();

    public MealPlanGeneratorPage(MealPlanCreatorExecutor mealPlanCreator, MealPlan mealPlan) {
        this(mealPlanCreator);
        this.mealPlanBuilder = mealPlanCreator.getMealPlanBuilder();
        columns.remove(0);
        for (DailyMealPlan dailyMealPlan : mealPlan.getDailyMealPlans()) {
            DailyMealPlanColumn dailyMealPlanColumn = new DailyMealPlanColumn(this);
            List<Meal> meals = dailyMealPlan.getMeals();
            dailyMealPlanColumn.addMeals(meals);
            columns.add(dailyMealPlanColumn);
        }
        draw();
    }

    public MealPlanGeneratorPage(MealPlanCreatorExecutor mealPlanCreator) {
        this.mealPlanCreator = mealPlanCreator;
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        doneButton.addActionListener( e -> {
            for (DailyMealPlanColumn column : columns) {
                mealPlanBuilder.addDailyMealPlan(column.getDailyMealPlan());
            }
            mealPlanCreator.saveMealPlan(mealPlanBuilder.build());
            setVisible(false);
            new MealPlanPage();
        });

        cancelButton.addActionListener( e -> {
            setVisible(false);
            new MealPlanUnavailablePage();
        });
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(1);
        columns.add(new DailyMealPlanColumn(this));
        topPanel.setLayout(gridLayout);
        setPreferredSize(new Dimension(800,370));
        setVisible(true);
        setResizable(false);
        draw();
        pack();
    }

    public void draw(){
        topPanel.removeAll();
        GridLayout layout = (GridLayout) topPanel.getLayout();
        layout.setColumns(columns.size() + 1);
        topPanel.setLayout(layout);
        for (DailyMealPlanColumn column : columns) {
            Component c = column.getComponent();
            c.setSize(new Dimension(3000,c.getHeight()));
            topPanel.add(c);
        }
        JButton addTableButton = new JButton("+");
        addTableButton.addActionListener( e ->{
            columns.add(new DailyMealPlanColumn(this));
            draw();
            revalidate();
        });

        topPanel.add(addTableButton);
    }



    public void updateTable(JTable table){

    }

}
