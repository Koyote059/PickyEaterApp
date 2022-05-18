package pickyeater.UI.app.mealplanpage.utils;

import pickyeater.UI.choosers.MealsChooser;
import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.builders.DailyMealPlanBuilder;
import pickyeater.builders.PickyDailyMealPlanBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DailyMealPlanColumn implements ActionListener, MouseListener {

    private final JPanel pane;
    private final JTable table;
    private final Frame frame;

    private final DailyMealPlanBuilder dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();

    public DailyMealPlanColumn(Frame frame){
        this.frame = frame;
        table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Name");
        tableModel.addColumn("Quantity");
        table.setModel(tableModel);
        table.addMouseListener(this);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setPreferredSize(new Dimension(150,250));
        pane = new JPanel(new BorderLayout());
        pane.add(BorderLayout.PAGE_START,scrollPane);
        JButton addMealButton = new JButton("+");
        addMealButton.addActionListener(this);
        pane.add(BorderLayout.PAGE_END,addMealButton);
        refresh();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MealsChooser chooser = new MealsChooser(frame);
        Optional<Meal> mealOptional = chooser.getMeal();
        if(mealOptional.isEmpty()) return;
        dailyMealPlanBuilder.addMeal(mealOptional.get());
        refresh();
    }

    public Component getComponent(){
        return pane;
    }

    private void refresh(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setNumRows(0);
        for (Meal meal : dailyMealPlanBuilder.getMeals()) {
            String mealName = meal.getName();
            DecimalFormat formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(2);
            float mealWeight = meal.getWeight();

            Object[] row = new Object[]{
              mealName,formatter.format(mealWeight) + " g"
            };
            model.addRow(row);

        }

        table.revalidate();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        int column = table.columnAtPoint(point);
        if(column==-1 || row == -1) return;
        dailyMealPlanBuilder.removeMeal(row);
        refresh();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void addMeals(List<Meal> meals) {
        meals.forEach(dailyMealPlanBuilder::addMeal);
        refresh();
    }

    public DailyMealPlan getDailyMealPlan() {
        return dailyMealPlanBuilder.build();
    }
}
