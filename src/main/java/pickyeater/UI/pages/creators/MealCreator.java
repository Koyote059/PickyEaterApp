package pickyeater.UI.pages.creators;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.UI.pages.choosers.FoodPopupMenu;
import pickyeater.UI.pages.choosers.IngredientChooser;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.QuantityType;
import pickyeater.builders.MealBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.creators.CreateMealExecutor;
import pickyeater.utils.IngredientQuantityConverter;
import pickyeater.utils.MealQuantityConverter;
import pickyeater.utils.MouseClickListener;
import pickyeater.utils.StringsUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealCreator extends JDialog {
    private final JTable ingredientsTable;
    private final JTextField mealNameField;
    private final JLabel txtInsertName = new JLabel("Insert here the meal's name: ");
    private final JPanel panelInsertName = new JPanel();
    private final JLabel mealQuantityTypeLabel = new JLabel(" g");
    private final JTextField mealQuantityTextField = new JTextField("100");
    private JPanel mealPanel = null;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final CreateMealExecutor executor = ExecutorProvider.getCreateMealExecutor();
    private final MealBuilder mealBuilder = executor.getMealBuilder();
    private Meal startingMeal = null;
    private boolean isMealEditing = false;

    public MealCreator(JFrame parent) {
        super(parent, "Meal Creator", true);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel customMealPanel = new JPanel(new BorderLayout());
        mealNameField = new JTextField();
        panelInsertName.setLayout(new BorderLayout());
        panelInsertName.add(BorderLayout.WEST, txtInsertName);
        panelInsertName.add(BorderLayout.CENTER, mealNameField);

        customMealPanel.add(BorderLayout.PAGE_START, panelInsertName);
        JButton addIngredientButton = new JButton("Add ingredient");
        addIngredientButton.addActionListener(e -> {
            IngredientChooser ingredientChooser = new IngredientChooser(parent);
            Optional<Ingredient> ingredientOptional = ingredientChooser.getIngredient();
            ingredientOptional.ifPresent(ingredient -> {
                mealBuilder.addIngredients(ingredient);
                ingredients.add(ingredient);
                draw();
            });
        });
        customMealPanel.add(BorderLayout.PAGE_END, addIngredientButton);
        mainPanel.add(BorderLayout.PAGE_START, customMealPanel);
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
        ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientsTable.removeEditor();
        ingredientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                showPieChart();
                pack();
                revalidate();
            }
        });
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(ingredientsTable);
        //scrollPane.setPreferredSize(new Dimension(150,250));
        mainPanel.add(BorderLayout.LINE_END, scrollPane);
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(BorderLayout.LINE_START, cancelButton);
        JButton doneButton = new JButton("Done");
        ingredientsTable.getTableHeader().setReorderingAllowed(false);
        ingredientsTable.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Point framePoint = parent.getLocation();
                    Point realPoint = new Point(point.x - framePoint.x, point.y - framePoint.y);
                    int selectedIndex = ingredientsTable.rowAtPoint(e.getPoint());
                    ingredientsTable.setRowSelectionInterval(selectedIndex, selectedIndex);
                    if (selectedIndex < 0)
                        return;
                    Ingredient selectedIngredient = ingredients.get(selectedIndex);
                    FoodPopupMenu popupMenu = new FoodPopupMenu();
                    popupMenu.addDeleteListener(l -> {
                        int choice = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete it?",
                                "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (choice != JOptionPane.YES_OPTION)
                            return;
                        ingredients.remove(selectedIngredient);
                        draw();
                    });
                    popupMenu.show(parent, realPoint.x, realPoint.y);
                }
            }
        });
        doneButton.addActionListener(e -> {
            String mealName = mealNameField.getText();
            if (isMealEditing && executor.isMealUsed(startingMeal)) {
                JOptionPane.showMessageDialog(getParent(), "You can't edit the meal's name as it's been used!");
                return;
            }
            if (mealName.length() > 30) {
                JOptionPane.showMessageDialog(getParent(), "Name is too long!");
                return;
            }
            if (mealName.length() < 3) {
                JOptionPane.showMessageDialog(getParent(), "Name is too short!");
                return;
            }
            if (!StringsUtils.isAlpha(mealName)) {
                JOptionPane.showMessageDialog(getParent(), "Name can only contain alphanumeric characters!");
                return;
            }
            if (mealBuilder.getIngredients().size() == 0) {
                JOptionPane.showMessageDialog(getParent(), "You must add at least 1 ingredient!");
                return;
            }
            if (!isMealEditing && executor.existsMeal(mealName)) {
                JOptionPane.showMessageDialog(getParent(), mealName + " already exists!");
                return;
            }
            mealBuilder.setName(StringsUtils.capitalize(mealName));
            Meal meal = mealBuilder.build();
            MealQuantityConverter mealQuantityConverter = executor.getMealQuantityConverter();

            Meal convertedMeal = mealQuantityConverter.convert(meal, 100);
            if (isMealEditing) {
                executor.deleteMeal(startingMeal);
            }
            executor.saveMeal(convertedMeal);

            dispose();
        });
        buttonPanel.add(BorderLayout.LINE_END, doneButton);
        mainPanel.add(BorderLayout.PAGE_END, buttonPanel);
        setContentPane(mainPanel);
        draw();
        pack();
        setSize(new Dimension(677, 507));
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void draw() {
        populateTable();
        if (ingredientsTable.getRowCount() > 0)
            ingredientsTable.setRowSelectionInterval(0, 0);
        showPieChart();
        pack();
        revalidate();
    }

    private void showPieChart() {
        int selectedItem = ingredientsTable.getSelectedRow();
        if (selectedItem == -1)
            return;
        Ingredient selectedIngredient = ingredients.get(selectedItem);
        QuantityType quantityType = selectedIngredient.getQuantity().getQuantityType();
        Ingredient highlightedIngredient;
        if(quantityType.equals(QuantityType.PIECES)){
            IngredientQuantityConverter converter = new IngredientQuantityConverter();
            highlightedIngredient = converter.convert(selectedIngredient,1);
        } else {
            highlightedIngredient = selectedIngredient;
        }

        Nutrients ingredientNutrients = highlightedIngredient.getNutrients();
        PieChart pieChart = new PieChart(300, 300);
        pieChart.setTitle(highlightedIngredient.getName());
        pieChart.addSeries("Proteins", ingredientNutrients.getProteins());
        pieChart.addSeries("Carbs", ingredientNutrients.getCarbs());
        pieChart.addSeries("Fats", ingredientNutrients.getFats());
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        XChartPanel<PieChart> chartPanel = new XChartPanel<>(pieChart);
        BorderLayout layout = (BorderLayout) getLayout();
        JPanel previousPanel = (JPanel) layout.getLayoutComponent(BorderLayout.LINE_START);
        if (previousPanel != null)
            remove(previousPanel);
        mealQuantityTextField.setText("100");
        if (mealPanel != null)
            remove(mealPanel);
        mealPanel = new JPanel(new BorderLayout());
        mealPanel.add(BorderLayout.PAGE_START, chartPanel);
        add(BorderLayout.LINE_START, mealPanel);
        revalidate();
    }

    private void populateTable() {
        DefaultTableModel tableModel = (DefaultTableModel) ingredientsTable.getModel();
        tableModel.setRowCount(0);
        for (Ingredient ingredient : ingredients) {
            String ingredientName = ingredient.getName();
            float ingredientQuantity = ingredient.getQuantity().getAmount();
            QuantityType quantityType = ingredient.getQuantity().getQuantityType();
            String suffix;
            switch (quantityType) {
                case GRAMS -> suffix = "gr";
                case MILLILITERS -> suffix = "ml";
                case PIECES -> suffix = "pz";
                default -> suffix = "Error";
            }
            DecimalFormat formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(2);
            Object[] row = new Object[]{ingredientName, formatter.format(ingredientQuantity) + " " + suffix};
            tableModel.addRow(row);
        }
    }

    public void createMeal() {
        setVisible(true);
    }

    public void editMeal(Meal meal) {
        isMealEditing = true;
        startingMeal = meal;
        mealNameField.setText(meal.getName());
        mealBuilder.addIngredients(meal.getIngredients().toArray(new Ingredient[0]));
        ingredients.addAll(meal.getIngredients());
        draw();
        setVisible(true);
    }
}