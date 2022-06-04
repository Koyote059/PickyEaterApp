package pickyeater.UI.pages.app.mealplanpage.utils;

import pickyeater.UI.pages.choosers.MealsChooser;
import pickyeater.basics.food.Meal;
import pickyeater.basics.mealplan.DailyMealPlan;
import pickyeater.builders.DailyMealPlanBuilder;
import pickyeater.builders.PickyDailyMealPlanBuilder;
import pickyeater.utils.MouseClickListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DailyMealPlanColumn implements ActionListener {
    private final JPanel pane;
    private final JTable table;
    private final JFrame parent;
    private final List<Meal> meals = new ArrayList<>();
    private final DailyMealPlanBuilder dailyMealPlanBuilder = new PickyDailyMealPlanBuilder();

    public DailyMealPlanColumn(JFrame parent) {
        this.parent = parent;
        table = new JTable();
        table.setToolTipText("Right click to move/remove");
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Name");
        tableModel.addColumn("Quantity");
        table.getTableHeader().setReorderingAllowed(false);
        table.setModel(tableModel);
        table.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    int selectedIndex = table.rowAtPoint(e.getPoint());
                    table.setRowSelectionInterval(selectedIndex, selectedIndex);
                    if (selectedIndex < 0)
                        return;
                    Meal selectedMeal = meals.get(selectedIndex);
                    ColumnPopupmenu popupMenu = new ColumnPopupmenu();
                    popupMenu.addRemoveListener(e1 -> {
                        dailyMealPlanBuilder.removeMeal(selectedIndex);
                        meals.remove(selectedMeal);
                        refresh();
                    });
                    popupMenu.addTakeUpListener(e1 -> {
                        if (selectedIndex == 0)
                            return;
                        Collections.swap(meals, selectedIndex, selectedIndex - 1);
                        refresh();
                    });
                    popupMenu.addTakeDownListener(e1 -> {
                        if (selectedIndex == (meals.size() - 1))
                            return;
                        Collections.swap(meals, selectedIndex, selectedIndex + 1);
                        refresh();
                    });
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setPreferredSize(new Dimension(150, 250));
        pane = new JPanel(new BorderLayout());
        pane.add(BorderLayout.CENTER, scrollPane);
        JButton addMealButton = new JButton("+");
        addMealButton.addActionListener(this);
        addMealButton.setToolTipText("Click to add meal");
        pane.add(BorderLayout.PAGE_END, addMealButton);
        refresh();
    }

    private void refresh() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setNumRows(0);
        for (Meal meal : meals) {
            String mealName = meal.getName();
            DecimalFormat formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(2);
            float mealWeight = meal.getWeight();
            Object[] row = new Object[]{mealName, formatter.format(mealWeight) + " g"};
            model.addRow(row);
        }
        table.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MealsChooser chooser = new MealsChooser(parent);
        Optional<Meal> mealOptional = chooser.getMeal();
        if (mealOptional.isEmpty())
            return;
        meals.add(mealOptional.get());
        dailyMealPlanBuilder.addMeal(mealOptional.get());
        refresh();
    }

    public Component getComponent() {
        return pane;
    }

    public void addMeals(List<Meal> meals) {
        meals.forEach(dailyMealPlanBuilder::addMeal);
        this.meals.addAll(meals);
        refresh();
    }

    public DailyMealPlan getDailyMealPlan() {
        return dailyMealPlanBuilder.build();
    }
}
