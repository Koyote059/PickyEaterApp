package pickyeater.UI.pages.choosers;

import pickyeater.UI.pages.creators.MealCreator;
import pickyeater.utils.pagesutils.NutrientsPieChart;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealChooserExecutor;
import pickyeater.utils.MealQuantityConverter;
import pickyeater.utils.StringToNumber;
import pickyeater.utils.StringsUtils;

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

public class MealsChooser extends JDialog {
    private final MealChooserExecutor mealSearcherExecutor = ExecutorProvider.getMealChooserExecutor();
    private final NutrientsPieChart nutrientsPieChart;
    private JTextField searchBar;
    private JList mealsList;
    private JLabel txtSearchMeal = new JLabel("Search Meals: ");
    private JPanel panelSearchBar = new JPanel();
    private List<Meal> searchedMeals;
    private final JButton cancelButton;
    private Meal returningMeal = null;
    private JPanel mealPanel = new JPanel(new BorderLayout());
    private final JTextField mealQuantityTextField = new JTextField();
    private final JPanel mealQuantityPanel = new JPanel(new BorderLayout());
    private final JLabel mealQuantityTypeLabel = new JLabel("g");

    private final JPanel centerPanel = new JPanel(new GridLayout(1,2));
    private boolean isChoosing;


    public MealsChooser(JFrame parent) {
        super(parent, "Meals Chooser", true);
        setLayout(new BorderLayout());
        panelSearchBar.setLayout(new BorderLayout());
        panelSearchBar.add(BorderLayout.WEST, txtSearchMeal);
        panelSearchBar.add(BorderLayout.CENTER, searchBar);
        add(BorderLayout.NORTH, panelSearchBar);
        JPanel ingredientListPanel = new JPanel(new BorderLayout());

        ingredientListPanel.add(BorderLayout.CENTER, new JScrollPane(mealsList));
        add(BorderLayout.CENTER,centerPanel);
        centerPanel.add(mealPanel);
        centerPanel.add(ingredientListPanel);
        mealQuantityTextField.setToolTipText("Left void it'll put automatically 100g");
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchBar.getText();
                if(!StringsUtils.isAlpha(text)) searchedMeals = new ArrayList<>();
                else searchedMeals = new ArrayList<>(mealSearcherExecutor.getMealsThatStartWith(text));
                populateMealList();
            }
        });
        searchedMeals = new ArrayList<>(mealSearcherExecutor.getEveryMeal());
        populateMealList();
        mealsList.setMinimumSize(new Dimension(250, 250));
        if(isChoosing){
            mealsList.setToolTipText("Double click to check ingredients, right click to delete/edit meal");
        } else {
            mealsList.setToolTipText("Double click to check ingredients");
        }
        mealsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        mealsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                showPieChart();
            }
        });
        mealsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() < 2)
                    return;
                int selectedIndex = mealsList.getSelectedIndex();
                if (selectedIndex < 0)
                    return;
                Meal meal = searchedMeals.get(selectedIndex);
                setVisible(false);
                new MealInfoJDialog(parent, meal,getLocation()).setVisible(true);
                setVisible(true);
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> {
            if(!isChoosing) dispose();
            int selectedItem = mealsList.getSelectedIndex();
            if (selectedItem == -1) {
                dispose();
                return;
            }
            Meal meal = searchedMeals.get(selectedItem);
            MealQuantityConverter mealQuantityConverter = new MealQuantityConverter();
            float returningWeight;
            if (mealQuantityTextField.getText().isEmpty()) {
                returningWeight = 100;
            } else {
                try {
                    returningWeight = StringToNumber.convertPositiveFloatException(mealQuantityTextField.getText());
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(getParent(), "Insert valid quantity!");
                    return;
                }
            }
            returningMeal = mealQuantityConverter.convert(meal, returningWeight);
            dispose();
        });
        mealsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(isChoosing) return;
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    int selectedIndex = mealsList.locationToIndex(e.getPoint());
                    mealsList.setSelectedIndex(selectedIndex);
                    if (selectedIndex < 0)
                        return;
                    Meal selectedMeal = searchedMeals.get(selectedIndex);
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem deleteItem = new JMenuItem("Delete");
                    JMenuItem editItem = new JMenuItem("Edit");
                    // add menu items to popup
                    popup.add(deleteItem);
                    popup.addSeparator();
                    popup.add(editItem);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                    deleteItem.addActionListener(l -> {
                        if (mealSearcherExecutor.isMealUsed(selectedMeal)) {
                            JOptionPane.showMessageDialog(parent, "Cannot delete this meal as it's being used!");
                            return;
                        }
                        int choice = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete it?", "",
                                JOptionPane.YES_NO_OPTION);
                        if (choice != JOptionPane.YES_OPTION)
                            return;
                        mealSearcherExecutor.deleteMeal(selectedMeal);
                        searchedMeals.remove(selectedMeal);
                        populateMealList();
                        showPieChart();
                    });
                    editItem.addActionListener(l -> {
                        if(mealSearcherExecutor.isMealUsed(selectedMeal)) {
                            int choice = JOptionPane.showConfirmDialog(parent, "This meal is used somewhere. \nAre you sure you want to edit it?", "",
                                    JOptionPane.YES_NO_OPTION);
                            if (choice != JOptionPane.YES_OPTION)
                                return;
                        }
                        MealCreator creator = new MealCreator(parent,getLocation());
                        setVisible(false);
                        creator.editMeal(selectedMeal);
                        setVisible(true);
                        String text = searchBar.getText();
                        searchedMeals = new ArrayList<>(mealSearcherExecutor.getMealsThatStartWith(text));
                        populateMealList();
                        showPieChart();
                    });
                }
            }
        });
        nutrientsPieChart = new NutrientsPieChart();
        mealPanel.add(BorderLayout.CENTER, nutrientsPieChart.getPanel());
        mealQuantityPanel.add(BorderLayout.CENTER,mealQuantityTextField);
        mealQuantityPanel.setBorder(new EmptyBorder(5,10,0,10));
        mealQuantityTextField.setBorder(new EmptyBorder(0,5,0,0));
        mealQuantityPanel.add(BorderLayout.LINE_END, mealQuantityTypeLabel);
        mealPanel.add(BorderLayout.PAGE_END, mealQuantityPanel);
        add(BorderLayout.LINE_START, mealPanel);

        if (!searchedMeals.isEmpty()) {
            showPieChart();
        } else {
            JOptionPane.showMessageDialog(parent, "Error 404, Meals not found", "Error 404", JOptionPane.ERROR_MESSAGE);
        }

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(doneButton);
        add(BorderLayout.PAGE_END, buttonsPanel);
        setSize(new Dimension(677, 507));
        setResizable(false);
        setLocationRelativeTo(parent);
    }
        private void populateMealList() {
        Comparator<? super Meal> comparator = Comparator.comparing(Meal::getName);
        searchedMeals.sort(comparator);

        Object[] listData = new Object[searchedMeals.size()];

        for (int i = 0; i < searchedMeals.size(); i++) {
            Meal meal = searchedMeals.get(i);
            listData[i] = meal.getName();
        }
        mealsList.setListData(listData);
        if (searchedMeals.size() != 0) {
            mealsList.setSelectedIndex(0);
        }
        repaint();
    }

    private void showPieChart() {
        int selectedItem = mealsList.getSelectedIndex();
        if (selectedItem == -1)
            return;
        Meal selectedMeal = searchedMeals.get(selectedItem);
        Nutrients mealNutrients = selectedMeal.getNutrients();
        nutrientsPieChart.setNutrients(mealNutrients);
        nutrientsPieChart.setName(selectedMeal.getName());
        revalidate();
    }

    public void manageMeals() {
        setTitle("Manage meals");
        cancelButton.setVisible(false);
        mealQuantityPanel.setVisible(false);
        isChoosing = false;
        setVisible(true);
    }

    public Optional<Meal> getMeal() {
        setTitle("Choose meal");
        cancelButton.setVisible(true);
        mealQuantityPanel.setVisible(true);
        isChoosing = true;
        setVisible(true);
        return Optional.ofNullable(returningMeal);
    }
}
