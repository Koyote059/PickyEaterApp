package pickyeater.UI.pages.creators;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.choosers.IngredientChooser;
import pickyeater.UI.pages.leftbuttons.PanelButtons;
import pickyeater.utils.pagesutils.NutrientsPieChart;
import pickyeater.basics.food.*;
import pickyeater.basics.groceries.PickyFinder;
import pickyeater.builders.MealBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.creators.CreateMealExecutor;
import pickyeater.utils.StringsUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
    private NutrientsPieChart nutrientsPieChart;

    public MealCreator(JFrame parent,Point location) {
        super(parent, "Meal Creator", true);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel customMealPanel = new JPanel(new BorderLayout());
        mealNameField = new JTextField();
        mealNameField.addActionListener( listener -> showPieChart());
        panelInsertName.setLayout(new BorderLayout());
        panelInsertName.add(BorderLayout.WEST, txtInsertName);
        panelInsertName.add(BorderLayout.CENTER, mealNameField);

        customMealPanel.add(BorderLayout.PAGE_START, panelInsertName);
        JButton addIngredientButton = new JButton("Add ingredient");
        addIngredientButton.addActionListener(e -> {
            IngredientChooser ingredientChooser = new IngredientChooser(parent);
            Optional<Ingredient> ingredientOptional = ingredientChooser.getIngredient();
            ingredientOptional.ifPresent(addingIngredient -> {

                for (Iterator<Ingredient> iterator = ingredients.iterator(); iterator.hasNext(); ) {
                    Ingredient ingredient = iterator.next();
                    if(ingredient.getName().equals(addingIngredient.getName())){
                        mealBuilder.remove(ingredient);
                        addingIngredient = new PickyFinder().sumIngredient(ingredient,addingIngredient);
                        iterator.remove();
                        break;
                    }
                }

                mealBuilder.addIngredients(addingIngredient);
                ingredients.add(addingIngredient);
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
        scrollPane.setPreferredSize(new Dimension(250, 250));
        scrollPane.setViewportView(ingredientsTable);
        //scrollPane.setPreferredSize(new Dimension(150,250));
        mainPanel.add(BorderLayout.LINE_END, scrollPane);
        JPanel buttonPanel = new JPanel(new GridLayout());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(BorderLayout.LINE_START, cancelButton);
        JButton doneButton = new JButton("Done");
        ingredientsTable.getTableHeader().setReorderingAllowed(false);
        ingredientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    int selectedIndex = ingredientsTable.rowAtPoint(e.getPoint());
                    ingredientsTable.setRowSelectionInterval(selectedIndex, selectedIndex);
                    if (selectedIndex < 0)
                        return;
                    Ingredient selectedIngredient = ingredients.get(selectedIndex);
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem deleteItem = new JMenuItem("Remove");
                    popup.add(deleteItem);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                    deleteItem.addActionListener(l -> {
                        int choice = JOptionPane.showConfirmDialog(parent, "Are you sure you want to remove it?",
                                "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (choice != JOptionPane.YES_OPTION)
                            return;
                        ingredients.remove(selectedIngredient);
                        mealBuilder.remove(selectedIngredient);
                        draw();
                    });
                }
            }
        });
        doneButton.addActionListener(e -> {
            String mealName = mealNameField.getText();
            if (isMealEditing && executor.isMealUsed(startingMeal) && (!startingMeal.getName().equals(mealName))) {
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

            if (isMealEditing) {
                executor.deleteMeal(startingMeal);
            }
            executor.saveMeal(meal);

            new MainFrame();
            MainFrame.changePage(PanelButtons.SETTINGS);
            parent.dispose();
            dispose();
        });
        buttonPanel.add(BorderLayout.CENTER, doneButton);
        mainPanel.add(BorderLayout.PAGE_END, buttonPanel);
        setContentPane(mainPanel);
        pack();

        nutrientsPieChart = new NutrientsPieChart();
        add(BorderLayout.LINE_START, nutrientsPieChart.getPanel());
        draw();

        setSize(new Dimension(677, 507));
        setPreferredSize(new Dimension(677, 507));
        setResizable(false);
        setLocation(location);
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

        NutrientsAccumulator nutrientsAccumulator = new PickyNutrientsAccumulator();
        ingredients.forEach( ingredient -> nutrientsAccumulator.sumNutrients(ingredient.getNutrients()));
        nutrientsPieChart.setNutrients(nutrientsAccumulator.generateNutrients());
        nutrientsPieChart.setName(mealNameField.getText());
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