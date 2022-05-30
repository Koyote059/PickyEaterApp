package pickyeater.UI.pages.app.mealplanpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.pages.app.mealplanpage.utils.DailyMealPlanColumn;
import pickyeater.UI.pages.leftbuttons.PanelButtons;
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
            parent.setSize(new Dimension(677,507));
            mealPlanCreator.saveMealPlan(mealPlanBuilder.build());
            MainFrame.changePage(PanelButtons.DIET);

        });

        cancelButton.addActionListener( e -> {
            parent.setSize(new Dimension(677,507));
            MainFrame.changePage(PanelButtons.DIET);
        });
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(1);
        columns.add(new DailyMealPlanColumn(parent));
        setPreferredSize(new Dimension(677,507));
        setSize(new Dimension(677,507));
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
