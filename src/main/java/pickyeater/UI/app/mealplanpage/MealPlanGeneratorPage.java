package pickyeater.UI.app.mealplanpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.app.mealplanpage.utils.DailyMealPlanColumn;
import pickyeater.UI.leftbuttons.PanelButtons;
import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.basics.mealplan.MealPlan;
import pickyeater.builders.MealPlanBuilder;
import pickyeater.executors.MealPlanCreatorExecutor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MealPlanGeneratorPage extends PickyPage {
    private JPanel mainPanel;
    private JButton doneButton;
    private JButton cancelButton;

    private JPanel topPanel;
    private JScrollPane scrollPane;
    private MealPlanBuilder mealPlanBuilder;

    List<DailyMealPlanColumn> columns = new ArrayList<>();

    public MealPlanGeneratorPage(MealPlanCreatorExecutor mealPlanCreator, MealPlan mealPlan, JFrame parent) {
        this(mealPlanCreator,parent);
        columns.remove(0);
        for (DailyMealPlan dailyMealPlan : mealPlan.getDailyMealPlans()) {
            DailyMealPlanColumn dailyMealPlanColumn = new DailyMealPlanColumn(parent);
            List<Meal> meals = dailyMealPlan.getMeals();
            dailyMealPlanColumn.addMeals(meals);
            columns.add(dailyMealPlanColumn);
        }

    }

    public MealPlanGeneratorPage(MealPlanCreatorExecutor mealPlanCreator,JFrame parent) {
        super(parent);

        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        this.mealPlanBuilder = mealPlanCreator.getMealPlanBuilder();
        doneButton.addActionListener( e -> {
            for (DailyMealPlanColumn column : columns) {
                mealPlanBuilder.addDailyMealPlan(column.getDailyMealPlan());
            }
            mealPlanCreator.saveMealPlan(mealPlanBuilder.build());
            MainFrame.changePage(PanelButtons.DIET);

        });

        cancelButton.addActionListener( e -> MainFrame.changePage(PanelButtons.DIET));
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(1);
        columns.add(new DailyMealPlanColumn(parent));
        setPreferredSize(new Dimension(800,370));
    }

    public void draw(){
        topPanel.removeAll();
        GridLayout layout = new GridLayout();
        layout.setColumns(columns.size() + 1);
        topPanel.setLayout(layout);
        for (DailyMealPlanColumn column : columns) {
            Component c = column.getComponent();
            c.setSize(new Dimension(3000,c.getHeight()));
            topPanel.add(c);
        }
        JButton addTableButton = new JButton("+");
        addTableButton.addActionListener( e ->{
            columns.add(new DailyMealPlanColumn(parent));
            draw();
            revalidate();
        });
        topPanel.add(addTableButton);
        parent.pack();
    }


    @Override
    public void showPage(){
        draw();
        super.showPage();
    }


    private void createUIComponents() {
        scrollPane = new JScrollPane(topPanel);
    }
}
