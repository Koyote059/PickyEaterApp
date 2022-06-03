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
import pickyeater.utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IngredientChooser extends JDialog {
    private final IngredientSearcherExecutor ingredientsSearcherExecutor = ExecutorProvider.getIngredientSearcherExecutor();
    private final JTextField searchBar;
    private final JList ingredientsList;
    private List<Ingredient> searchedIngredients;
    private final JButton cancelButton;
    private JPanel mealPanel = new JPanel(new BorderLayout());
    private Ingredient returningIngredient = null;
    private final JPanel ingredientQuantityPanel = new JPanel(new BorderLayout());
    private final JTextField ingredientQuantityTextField = new JTextField();
    private final JLabel ingredientQuantityTypeLabel = new JLabel(" g");
    private final JPanel centerPanel = new JPanel(new GridLayout(1,2));
    private boolean isChoosing = false;
    private JPopupMenu popup;
    private final JMenuItem deleteItem = new JMenuItem("Delete");
    private final JMenuItem editItem = new JMenuItem("Edit");

    public IngredientChooser(JFrame parent) {
        super(parent, "Ingredient Chooser", true);
        add(new JPanel());
        searchBar = new JTextField();
        ingredientsList = new JList();
        if(!isChoosing){
            ingredientsList.setToolTipText("Right click to delete/edit ingredient");
        }
        setLayout(new BorderLayout());
        JPanel ingredientListPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(ingredientsList);
        ingredientListPanel.add(BorderLayout.CENTER, scrollPane);
        JPanel panelSearchBar = new JPanel();
        panelSearchBar.setLayout(new BorderLayout());
        JLabel txtSearchIngredient = new JLabel("Search Ingredients: ");
        panelSearchBar.add(BorderLayout.WEST, txtSearchIngredient);
        panelSearchBar.add(BorderLayout.CENTER, searchBar);
        add(BorderLayout.NORTH, panelSearchBar);
        add(BorderLayout.CENTER,centerPanel);
        centerPanel.add(mealPanel);
        centerPanel.add(ingredientListPanel);
        Comparator<? super Ingredient> comparator = Comparator.comparing(Ingredient::getName);
        ingredientQuantityTextField.setToolTipText("Left void it'll put automatically 100g/100ml/1pz");
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = searchBar.getText();
                if(!StringsUtils.isAlpha(text)) searchedIngredients = new ArrayList<>();
                else searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getIngredientsThatStartWith(text));
                searchedIngredients.sort(comparator);
                populateIngredientsList();
            }
        });
        searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getAllIngredients());
        searchedIngredients.sort(comparator);
        populateIngredientsList();
        ingredientsList.setMinimumSize(new Dimension(300, 300));
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
            JOptionPane.showMessageDialog(parent, "Error 404, Ingredients not found", "Error 404", JOptionPane.ERROR_MESSAGE);
        }
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> {
            int selectedItem = ingredientsList.getSelectedIndex();
            Ingredient ingredient = searchedIngredients.get(selectedItem);
            IngredientQuantityConverter ingredientQuantityConverter = new IngredientQuantityConverter();
            int returningWeight;
            if (ingredientQuantityTextField.getText().isEmpty()) {
                if (ingredient.getQuantity().getQuantityType() == QuantityType.PIECES) {    // If pices
                    returningWeight = 1;
                } else {        // if g/ml
                    returningWeight = 100;
                }
            } else {
                returningWeight = StringToNumber.convertPositiveInteger(ingredientQuantityTextField.getText());
            }
            returningIngredient = ingredientQuantityConverter.convert(ingredient, returningWeight);
            dispose();
        });
        ingredientsList.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isChoosing) return;
                if (SwingUtilities.isRightMouseButton(e)) {
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Point framePoint = parent.getLocation();
                    Point realPoint = new Point(point.x - framePoint.x, point.y - framePoint.y);
                    int selectedIndex = ingredientsList.locationToIndex(e.getPoint());
                    if (selectedIndex < 0)
                        return;
                    ingredientsList.setSelectedIndex(selectedIndex);
                    Ingredient selectedIngredient = searchedIngredients.get(selectedIndex);
                    popup = new JPopupMenu();
                    // add menu items to popup
                    popup.add(deleteItem);
                    popup.addSeparator();
                    popup.add(editItem);
                    ingredientsList.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent me) {
                            showPopup(me); // showPopup() is our own user-defined method
                        }
                    });
                    deleteItem.addActionListener(l -> {
                        if (ingredientsSearcherExecutor.isIngredientUsed(selectedIngredient)) {
                            JOptionPane.showMessageDialog(parent, "Cannot delete this meal as it's being used!");
                            return;
                        }
                        int choice = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete it?");
                        if (choice != JOptionPane.YES_OPTION)
                            return;
                        ingredientsSearcherExecutor.deleteIngredient(selectedIngredient);
                        searchedIngredients.remove(selectedIngredient);
                        populateIngredientsList();
                        showPieChart();
                    });
                    editItem.addActionListener(l -> {
                        IngredientCreator creator = new IngredientCreator(parent);
                        creator.editIngredient(selectedIngredient);
                        String text = searchBar.getText();
                        searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getIngredientsThatStartWith(text));
                        populateIngredientsList();
                        showPieChart();
                    });
                }
            }
        });
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(doneButton);
        add(BorderLayout.PAGE_END, buttonsPanel);
        setSize(new Dimension(677, 507));
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void populateIngredientsList() {
        Object[] listData = new Object[searchedIngredients.size()];
        for (int i = 0; i < searchedIngredients.size(); i++) {
            Ingredient ingredient = searchedIngredients.get(i);
            listData[i] = ingredient.getName();
        }
        ingredientsList.setListData(listData);
        if (searchedIngredients.size() != 0) {
            ingredientsList.setSelectedIndex(0);
        }
        repaint();
    }

    private void showPieChart() {
        int selectedItem = ingredientsList.getSelectedIndex();
        Ingredient selectedIngredient = searchedIngredients.get(selectedItem);
        QuantityType quantityType = selectedIngredient.getQuantity().getQuantityType();
        ingredientQuantityTypeLabel.setText(ValuesConverter.convertQuantityTypeValue(quantityType));
        Ingredient highLightedIngredient;
        if(quantityType.equals(QuantityType.PIECES)){
            IngredientQuantityConverter converter = new IngredientQuantityConverter();
            highLightedIngredient = converter.convert(selectedIngredient,1);
        } else {
            highLightedIngredient = selectedIngredient;
        }
        Nutrients ingredientNutrients = highLightedIngredient.getNutrients();

        PieChart pieChart = new PieChart(300, 300);
        pieChart.setTitle(highLightedIngredient.getName());
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
        mealPanel.removeAll();
        mealPanel.add(BorderLayout.CENTER, chartPanel);
        ingredientQuantityPanel.setBorder(new EmptyBorder(5,10,0,10));
        ingredientQuantityTextField.setBorder(new EmptyBorder(0,5,0,0));
        ingredientQuantityPanel.removeAll();
        ingredientQuantityPanel.add(BorderLayout.CENTER,ingredientQuantityTextField);

        ingredientQuantityPanel.add(BorderLayout.LINE_END,ingredientQuantityTypeLabel);
        mealPanel.add(BorderLayout.PAGE_END, ingredientQuantityPanel);
        add(BorderLayout.LINE_START, mealPanel);
        revalidate();
    }

    public void manageIngredients() {
        cancelButton.setVisible(false);
        ingredientQuantityPanel.setVisible(false);
        isChoosing = false;
        setVisible(true);
    }

    public Optional<Ingredient> getIngredient() {
        cancelButton.setVisible(true);
        ingredientQuantityPanel.setVisible(true);
        isChoosing = true;
        setVisible(true);
        return Optional.ofNullable(returningIngredient);
    }

    private void showPopup(MouseEvent me) {
        if(me.isPopupTrigger())
            popup.show(me.getComponent(), me.getX(), me.getY());
    }
}