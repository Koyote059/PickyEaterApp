package pickyeater.UI.pages.creators;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import pickyeater.UI.pages.choosers.FoodPopupMenu;
import pickyeater.UI.pages.choosers.IngredientChooser;
import pickyeater.basics.food.*;
import pickyeater.builders.MealBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.creators.CreateMealExecutor;
import pickyeater.utils.Checker;
import pickyeater.utils.MealQuantityConverter;
import pickyeater.utils.MouseClickListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealCreator extends JDialog  {

    private JPanel mainPanel;

    private JTable ingredientsTable;
    private JTextField mealNameField;

    private List<Ingredient> ingredients = new ArrayList<>();

    private CreateMealExecutor executor = ExecutorProvider.getCreateMealExecutor();
    private MealBuilder mealBuilder = executor.getMealBuilder();
    private Meal editingMeal = null;
    public MealCreator(JFrame parent) {
        super(parent,"Meal Creator",true);
        mainPanel = new JPanel(new BorderLayout());
        JPanel customMealPanel = new JPanel(new BorderLayout());
        mealNameField = new JTextField("Insert here the meal's name");
        customMealPanel.add(BorderLayout.PAGE_START,mealNameField);
        JButton addIngredientButton = new JButton("Add ingredient");
        addIngredientButton.addActionListener(e -> {
            IngredientChooser ingredientChooser = new IngredientChooser(parent);
            Optional<Ingredient> ingredientOptional = ingredientChooser.getIngredient();
            ingredientOptional.ifPresent( ingredient -> {
                mealBuilder.addIngredients(ingredient);
                ingredients.add(ingredient);
                draw();
            });
        });
        customMealPanel.add(BorderLayout.PAGE_END,addIngredientButton);
        mainPanel.add(BorderLayout.PAGE_START,customMealPanel);

        DefaultTableModel tableModel = new DefaultTableModel(){
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
        buttonPanel.add(BorderLayout.LINE_START,cancelButton);

        JButton doneButton = new JButton("Done");

        ingredientsTable.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Point framePoint = parent.getLocation();
                    Point realPoint = new Point(point.x - framePoint.x,point.y - framePoint.y);
                    int selectedIndex = ingredientsTable.rowAtPoint(e.getPoint());
                    ingredientsTable.setRowSelectionInterval(selectedIndex,selectedIndex);
                    if(selectedIndex<0) return;
                    Ingredient selectedIngredient = ingredients.get(selectedIndex);
                    FoodPopupMenu popupMenu = new FoodPopupMenu();
                    popupMenu.addDeleteListener(l -> {
                        int choice = JOptionPane.showConfirmDialog(parent,"Are you sure you want to delete it?");
                        if(choice != JOptionPane.YES_OPTION) return;
                        ingredients.remove(selectedIngredient);
                        draw();
                    });

                    popupMenu.addEditListener( l -> {
                        IngredientCreator creator = new IngredientCreator(parent);
                        Ingredient newIngredient = creator.editIngredient(selectedIngredient);
                        if(selectedIngredient == newIngredient) return;
                        ingredients.remove(selectedIngredient);
                        ingredients.add(newIngredient);
                        draw();
                    });

                    popupMenu.show(parent,realPoint.x, realPoint.y);

                }
            }
        });

        doneButton.addActionListener(e ->{
            String mealName = mealNameField.getText();

            if(mealName.length()>20){
                JOptionPane.showMessageDialog(getParent(),"Name is too long!");
                return;
            }

            if(mealName.length()<3){
                JOptionPane.showMessageDialog(getParent(),"Name is too short!");
                return;
            }

            if(!Checker.isAlpha(mealName)) {
                JOptionPane.showMessageDialog(getParent(),"Name can only contain alphanumeric characters!");
                return;
            }

            if(mealBuilder.getIngredients().size()==0){
                JOptionPane.showMessageDialog(getParent(),"You must add at least 1 ingredient!");
                return;
            }

            mealBuilder.setName(mealName);
            Meal meal = mealBuilder.build();
            MealQuantityConverter mealQuantityConverter = executor.getMealQuantityConverter();
            Meal convertedMeal = mealQuantityConverter.convert(meal,100);
            if(editingMeal != null){
                editingMeal = convertedMeal;
            } else {
                executor.saveMeal(convertedMeal);
            }
            dispose();
        });
        buttonPanel.add(BorderLayout.LINE_END,doneButton);

        mainPanel.add(BorderLayout.PAGE_END, buttonPanel);
        setContentPane(mainPanel);

        draw();
        pack();

        setSize(new Dimension(677,507));
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void draw() {
        populateTable();
        if(ingredientsTable.getRowCount()>0) ingredientsTable.setRowSelectionInterval(0,0);
        showPieChart();
        pack();
        revalidate();
    }

    private void populateTable(){
        DefaultTableModel tableModel = (DefaultTableModel) ingredientsTable.getModel();
        tableModel.setRowCount(0);
        for (Ingredient ingredient : ingredients) {
            String ingredientName = ingredient.getName();
            float ingredientQuantity = ingredient.getQuantity().getAmount();
            QuantityType quantityType = ingredient.getQuantity().getQuantityType();
            String suffix = "";
            switch (quantityType) {
                case GRAMS -> suffix = "gr";
                case MILLILITERS -> suffix = "ml";
                case PIECES -> suffix = "pz";
                default -> suffix = "";
            }
            DecimalFormat formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(2);
            Object[] row = new Object[]{
                    ingredientName, formatter.format(ingredientQuantity) + " " + suffix
            };
            tableModel.addRow(row);
        }
    }

    private void showPieChart(){
        int selectedItem = ingredientsTable.getSelectedRow();
        if(selectedItem==-1) return;
        Ingredient ingredient = ingredients.get(selectedItem);
        Nutrients ingredientNutrients = ingredient.getNutrients();
        PieChart pieChart = new PieChart(300,300);
        pieChart.setTitle(ingredient.getName());
        pieChart.addSeries("Proteins",ingredientNutrients.getProteins());
        pieChart.addSeries("Carbs",ingredientNutrients.getCarbs());
        pieChart.addSeries("Fats",ingredientNutrients.getFats());
        JPanel ingredientPane = new JPanel(new BorderLayout());
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipsEnabled(true);
        XChartPanel<PieChart> chartPanel = new XChartPanel<>(pieChart);
        BorderLayout layout = (BorderLayout) getLayout();
        JPanel previousPanel = (JPanel) layout.getLayoutComponent(BorderLayout.LINE_START);
        if(previousPanel!=null) remove(previousPanel);
        ingredientPane.add(BorderLayout.PAGE_START,chartPanel);
        mainPanel.add(BorderLayout.LINE_START,chartPanel);

    }

    public void createMeal() {
        setVisible(true);
    }

    public Meal editMeal(Meal meal) {
        editingMeal = meal;
        mealNameField.setText(meal.getName());
        ingredients.addAll(meal.getIngredients());
        draw();
        setVisible(true);
        return editingMeal;
    }
}
