package pickyeater.UI.pages.choosers;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.UI.pages.creators.IngredientCreator;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.QuantityType;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.utils.IngredientQuantityConverter;
import pickyeater.utils.MouseClickListener;
import pickyeater.utils.StringToNumber;
import pickyeater.utils.ValuesConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IngredientChooser extends JDialog {
    private JTextField searchBar;
    private JList ingredientsList;
    private JLabel txtSearchIngredient = new JLabel("Search Ingredients:");
    private JPanel panelSearchBar = new JPanel();
    private List<Ingredient> searchedIngredients;
    private JButton cancelButton;
    private XChartPanel<PieChart> chartPanel;

    private JPanel mealPanel = null;
    private Ingredient returningIngredient = null;

    private JPanel ingredientQuantityPanel = new JPanel(new GridBagLayout());
    private JTextField ingredientQuantityTextField = new JTextField();
    private final IngredientSearcherExecutor ingredientsSearcherExecutor = ExecutorProvider.getIngredientSearcherExecutor();
    private JLabel ingredientQuantityTypeLabel = new JLabel(" g");

    public IngredientChooser(JFrame parent) {
        super(parent,"Ingredient Chooser",true);
        add(new JPanel());
        searchBar = new JTextField();
        ingredientsList = new JList();
        ingredientsList.setToolTipText("Right click to delete/edit ingredient");
        setLayout(new BorderLayout());

        JPanel ingredientListPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(ingredientsList);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        ingredientListPanel.add(scrollPane,constraints);
        JButton addIngredientButton = new JButton("Create ingredient");
        constraints.gridy=1;
        ingredientListPanel.add(addIngredientButton,constraints);
        panelSearchBar.add(BorderLayout.WEST, txtSearchIngredient);
        panelSearchBar.add(BorderLayout.EAST, searchBar);
        add(BorderLayout.NORTH, panelSearchBar);
        add(BorderLayout.LINE_END, ingredientListPanel);
        ingredientQuantityTextField.setToolTipText("If left void it puts automatically 100g");

        addIngredientButton.addActionListener(l -> {
            IngredientCreator creator = new IngredientCreator(parent);
            creator.createIngredient();
            searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getAllIngredients());
            populateIngredientsList();
            showPieChart();
        });
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = searchBar.getText();
                searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getIngredientsThatStartWith(text));
                populateIngredientsList();
            }
        });

        searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getAllIngredients());
        populateIngredientsList();
        ingredientsList.setMinimumSize(new Dimension(300,300));
        ingredientsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ingredientsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                showPieChart();
            }
        });


        if (!searchedIngredients.isEmpty()) {
            showPieChart();
        } else {
            JOptionPane.showMessageDialog(parent, "Error 404, Ingredients not found", "Error 404",
                    JOptionPane.ERROR_MESSAGE);
        }
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( e -> dispose());
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener( e ->{
            int selectedItem = ingredientsList.getSelectedIndex();
            Ingredient ingredient = searchedIngredients.get(selectedItem);
            IngredientQuantityConverter ingredientQuantityConverter = new IngredientQuantityConverter();
            int returningWeight;
            if (ingredientQuantityTextField.getText().isEmpty()) {
                returningWeight = 100;
            } else {
                returningWeight = StringToNumber.convertPositiveInteger(ingredientQuantityTextField.getText());
            }
            returningIngredient = ingredientQuantityConverter.convert(ingredient, returningWeight);
            dispose();
        });

        ingredientsList.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Point framePoint = parent.getLocation();
                    Point realPoint = new Point(point.x - framePoint.x,point.y - framePoint.y);
                    int selectedIndex = ingredientsList.locationToIndex(e.getPoint());
                    if(selectedIndex<0) return;
                    ingredientsList.setSelectedIndex(selectedIndex);
                    Ingredient selectedIngredient = searchedIngredients.get(selectedIndex);
                    FoodPopupMenu popupMenu = new FoodPopupMenu();
                    popupMenu.addDeleteListener(l -> {
                        if(ingredientsSearcherExecutor.isIngredientUsed(selectedIngredient)){
                            JOptionPane.showMessageDialog(parent,"Cannot delete this ingredient as it's being used!");
                            return;
                        }
                        int choice = JOptionPane.showConfirmDialog(parent,"Are you sure you want to delete it?");
                        if(choice != JOptionPane.YES_OPTION) return;
                        ingredientsSearcherExecutor.deleteIngredient(selectedIngredient);
                        searchedIngredients.remove(selectedIngredient);
                        populateIngredientsList();
                        showPieChart();
                    });

                    popupMenu.addEditListener( l -> {
                        IngredientCreator creator = new IngredientCreator(parent);
                        creator.editIngredient(selectedIngredient);
                        String name = searchBar.getText();
                        searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getIngredientsThatStartWith(name));
                        populateIngredientsList();
                        showPieChart();
                    });

                    popupMenu.show(parent,realPoint.x, realPoint.y);

                }
            }
        });
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(doneButton);
        add(BorderLayout.PAGE_END,buttonsPanel);

        setSize(new Dimension(677,507));
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void populateIngredientsList(){
        Object[] listData = new Object[searchedIngredients.size()];
        for (int i = 0; i < searchedIngredients.size(); i++) {
            Ingredient ingredient = searchedIngredients.get(i);
            listData[i] = ingredient.getName();
        }
        ingredientsList.setListData(listData);
        if(searchedIngredients.size()!=0) {
            ingredientsList.setSelectedIndex(0);
        }
        repaint();

    }

    private void showPieChart(){
        int selectedItem = ingredientsList.getSelectedIndex();
        Ingredient selectedIngredient = searchedIngredients.get(selectedItem);
        QuantityType quantityType = selectedIngredient.getQuantity().getQuantityType();
        ingredientQuantityTypeLabel.setText(ValuesConverter.convertQuantityTypeValue(quantityType));
        Nutrients ingredientNutrients = selectedIngredient.getNutrients();
        PieChart pieChart = new PieChart(300,300);
        pieChart.setTitle(selectedIngredient.getName());
        pieChart.addSeries("Proteins",ingredientNutrients.getProteins());
        pieChart.addSeries("Carbs",ingredientNutrients.getCarbs());
        pieChart.addSeries("Fats",ingredientNutrients.getFats());
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        chartPanel = new XChartPanel<>(pieChart);
        BorderLayout layout = (BorderLayout) getLayout();
        JPanel previousPanel = (JPanel) layout.getLayoutComponent(BorderLayout.LINE_START);
        if(previousPanel!=null) remove(previousPanel);
        if(mealPanel!=null) remove(mealPanel);
        mealPanel = new JPanel(new BorderLayout());
        mealPanel.add(BorderLayout.PAGE_START,chartPanel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.ipadx=1;
        constraints.gridwidth= 20;

        ingredientQuantityPanel.add(ingredientQuantityTextField,constraints);
        constraints.gridx = 20;
        constraints.gridwidth = 1;

        ingredientQuantityPanel.add(ingredientQuantityTypeLabel,constraints);

        mealPanel.add(BorderLayout.PAGE_END,ingredientQuantityPanel);
        add(BorderLayout.LINE_START,mealPanel);
        revalidate();
    }

    public void manageIngredients() {
        cancelButton.setVisible(false);
        ingredientQuantityPanel.setVisible(false);
        setVisible(true);
    }

    public Optional<Ingredient> getIngredient(){
        setVisible(true);
        cancelButton.setVisible(true);
        ingredientQuantityPanel.setVisible(true);
        return Optional.ofNullable(returningIngredient);
    }
}