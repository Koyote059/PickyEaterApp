package pickyeater.UI.pages.choosers;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.QuantityType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Set;

public class MealInfoJDialog extends JDialog {
    private final JTable ingredientsTable;

    public MealInfoJDialog(Frame parent, Meal meal) {
        super(parent, meal.getName(), true);
        setName("Meal Info - " + meal.getName());
        PieChart pieChart = new PieChart(410, 330);
        pieChart.addSeries("Proteins", 120);
        pieChart.addSeries("Carbs", 200);
        pieChart.addSeries("Fats", 40);
        JPanel chartPanel = new XChartPanel<>(pieChart);
        pieChart.getStyler().setToolTipType(Styler.ToolTipType.xAndYLabels);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.LINE_START, chartPanel);
        JButton okButton = new JButton("Ok");
        okButton.setHorizontalAlignment(SwingConstants.CENTER);
        okButton.setVerticalAlignment(SwingConstants.CENTER);
        okButton.addActionListener(e -> dispose());
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Name");
        tableModel.addColumn("Quantity");
        ingredientsTable = new JTable(tableModel);
        ingredientsTable.setName("Ingredients");
        ingredientsTable.removeEditor();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(ingredientsTable);
        scrollPane.setPreferredSize(new Dimension(150, 250));
        mainPanel.add(BorderLayout.LINE_END, scrollPane);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        mainPanel.add(BorderLayout.PAGE_END, buttonPanel);
        setResizable(false);
        populateTable(meal.getIngredients());
        setContentPane(mainPanel);
        pack();
    }

    private void populateTable(Set<Ingredient> ingredients) {
        DefaultTableModel tableModel = (DefaultTableModel) ingredientsTable.getModel();
        for (Ingredient ingredient : ingredients) {
            String ingredientName = ingredient.getName();
            float ingredientQuantity = ingredient.getQuantity().getAmount();
            QuantityType quantityType = ingredient.getQuantity().getQuantityType();
            String suffix;
            switch (quantityType) {
                case GRAMS -> suffix = "gr";
                case MILLILITERS -> suffix = "ml";
                case PIECES -> suffix = "pz";
                default -> suffix = "";
            }
            DecimalFormat formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(2);
            Object[] row = new Object[]{ingredientName, formatter.format(ingredientQuantity) + " " + suffix};
            tableModel.addRow(row);
        }
    }

    public void run() {
        this.setVisible(true);
    }
}
