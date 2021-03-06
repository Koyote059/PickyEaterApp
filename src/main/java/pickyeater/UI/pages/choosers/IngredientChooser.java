package pickyeater.UI.pages.choosers;

import pickyeater.UI.pages.creators.IngredientCreator;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.QuantityType;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.utils.IngredientQuantityConverter;
import pickyeater.utils.StringToNumber;
import pickyeater.utils.StringsUtils;
import pickyeater.utils.ValuesConverter;
import pickyeater.utils.pagesutils.NutrientsPieChart;

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
    private final NutrientsPieChart nutrientsPieChart;
    private final JButton cancelButton;
    private final JPanel ingredientQuantityPanel = new JPanel(new BorderLayout());
    private final JTextField ingredientQuantityTextField = new JTextField();
    private final JLabel ingredientQuantityTypeLabel = new JLabel(" g");
    private final JPanel centerPanel = new JPanel(new GridLayout(1, 2));
    private List<Ingredient> searchedIngredients;
    private final JPanel mealPanel = new JPanel(new BorderLayout());
    private Ingredient returningIngredient = null;
    private boolean isChoosing = false;

    public IngredientChooser(JFrame parent, Point location) {
        this(parent);
        setLocation(location);
    }

    public IngredientChooser(JFrame parent) {
        super(parent, "Ingredient Chooser", true);
        add(new JPanel());
        searchBar = new JTextField();
        ingredientsList = new JList();
        if (!isChoosing) {
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
        add(BorderLayout.CENTER, centerPanel);
        centerPanel.add(mealPanel);
        centerPanel.add(ingredientListPanel);
        ingredientQuantityTextField.setToolTipText("Left void it'll put automatically 100g/100ml/1pz");
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchBar.getText();
                if (!StringsUtils.isAlpha(text))
                    searchedIngredients = new ArrayList<>();
                else
                    searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getIngredientsThatStartWith(text));
                populateIngredientsList();
            }
        });
        searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getAllIngredients());
        populateIngredientsList();
        ingredientsList.setMinimumSize(new Dimension(250, 250));
        ingredientsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ingredientsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                showPieChart();
            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> {
            if (!isChoosing)
                dispose();
            int selectedItem = ingredientsList.getSelectedIndex();
            if (selectedItem == -1)
                return;
            Ingredient ingredient = searchedIngredients.get(selectedItem);
            IngredientQuantityConverter ingredientQuantityConverter = new IngredientQuantityConverter();
            float returningQuantity;
            if (ingredientQuantityTextField.getText().isEmpty()) {
                if (ingredient.getQuantity().getQuantityType() == QuantityType.PIECES) {
                    returningQuantity = 1;
                } else {
                    returningQuantity = 100;
                }
            } else {
                try {
                    returningQuantity = StringToNumber.convertPositiveFloatException(ingredientQuantityTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(parent, "Insert a valid quantity!");
                    return;
                }
            }
            returningIngredient = ingredientQuantityConverter.convert(ingredient, returningQuantity);
            dispose();
        });
        ingredientsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (isChoosing)
                    return;
                int selectedIndex = ingredientsList.locationToIndex(e.getPoint());
                if (selectedIndex < 0)
                    return;
                ingredientsList.setSelectedIndex(selectedIndex);
                Ingredient selectedIngredient = searchedIngredients.get(selectedIndex);
                if (e.getClickCount() == 2) {
                    IngredientInfoJDialog ingredientInfoJDialog = new IngredientInfoJDialog(parent, selectedIngredient, getLocation());
                    setVisible(false);
                    ingredientInfoJDialog.setVisible(true);
                    setVisible(true);
                    return;
                }
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu popup = new JPopupMenu();
                    // add menu items to popup
                    JMenuItem deleteItem = new JMenuItem("Delete");
                    JMenuItem editItem = new JMenuItem("Edit");
                    popup.add(deleteItem);
                    popup.addSeparator();
                    popup.add(editItem);
                    popup.show(e.getComponent(), e.getX(), e.getY()); // Shows popup
                    deleteItem.addActionListener(l -> {
                        if (ingredientsSearcherExecutor.isIngredientUsed(selectedIngredient)) {
                            JOptionPane.showMessageDialog(parent, "Cannot delete this meal as it's being used!");
                            return;
                        }
                        int choice = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete it?", "", JOptionPane.YES_NO_OPTION);
                        if (choice != JOptionPane.YES_OPTION)
                            return;
                        ingredientsSearcherExecutor.deleteIngredient(selectedIngredient);
                        searchedIngredients.remove(selectedIngredient);
                        populateIngredientsList();
                        showPieChart();
                    });
                    editItem.addActionListener(l -> {
                        if (ingredientsSearcherExecutor.isIngredientUsed(selectedIngredient)) {
                            int choice = JOptionPane.showConfirmDialog(parent, "This ingredient is used somewhere. \nAre you sure you want to edit it?", "", JOptionPane.YES_NO_OPTION);
                            if (choice != JOptionPane.YES_OPTION)
                                return;
                        }
                        IngredientCreator creator = new IngredientCreator(parent, getLocation());
                        setVisible(false);
                        creator.editIngredient(selectedIngredient);
                        setVisible(true);
                        String text = searchBar.getText();
                        searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getIngredientsThatStartWith(text));
                        populateIngredientsList();
                        showPieChart();
                    });
                }
            }
        });
        nutrientsPieChart = new NutrientsPieChart();
        mealPanel.add(BorderLayout.CENTER, nutrientsPieChart.getPanel());
        ingredientQuantityPanel.add(BorderLayout.CENTER, ingredientQuantityTextField);
        ingredientQuantityPanel.setBorder(new EmptyBorder(5, 10, 0, 10));
        ingredientQuantityTextField.setBorder(new EmptyBorder(0, 5, 0, 0));
        ingredientQuantityPanel.add(BorderLayout.LINE_END, ingredientQuantityTypeLabel);
        mealPanel.add(BorderLayout.PAGE_END, ingredientQuantityPanel);
        add(BorderLayout.LINE_START, mealPanel);
        if (!searchedIngredients.isEmpty()) {
            showPieChart();
        } else {
            JOptionPane.showMessageDialog(parent, "Error 404, Ingredients not found", "Error 404", JOptionPane.ERROR_MESSAGE);
        }
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(doneButton);
        add(BorderLayout.PAGE_END, buttonsPanel);
        setSize(new Dimension(677, 507));
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void populateIngredientsList() {
        ingredientsList.removeAll();
        Comparator<? super Ingredient> comparator = Comparator.comparing(Ingredient::getName);
        searchedIngredients.sort(comparator);
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
        if (selectedItem == -1)
            return;
        Ingredient selectedIngredient = searchedIngredients.get(selectedItem);
        QuantityType quantityType = selectedIngredient.getQuantity().getQuantityType();
        ingredientQuantityTypeLabel.setText(ValuesConverter.convertQuantityTypeValue(quantityType));
        Ingredient highLightedIngredient;
        if (quantityType.equals(QuantityType.PIECES)) {
            IngredientQuantityConverter converter = new IngredientQuantityConverter();
            highLightedIngredient = converter.convert(selectedIngredient, 1);
        } else {
            highLightedIngredient = selectedIngredient;
        }
        Nutrients ingredientNutrients = highLightedIngredient.getNutrients();
        nutrientsPieChart.setNutrients(ingredientNutrients);
        nutrientsPieChart.setName(highLightedIngredient.getName());
        revalidate();
    }

    public void manageIngredients() {
        setTitle(" Manage Ingredients ");
        cancelButton.setVisible(false);
        ingredientQuantityPanel.setVisible(false);
        isChoosing = false;
        setVisible(true);
    }

    public Optional<Ingredient> getIngredient() {
        setTitle(" Choose Ingredient ");
        cancelButton.setVisible(true);
        ingredientQuantityPanel.setVisible(true);
        isChoosing = true;
        setVisible(true);
        return Optional.ofNullable(returningIngredient);
    }
}