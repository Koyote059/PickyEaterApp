package pickyeater.UI.pages.choosers;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.UI.pages.creators.MealCreator;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealChooserExecutor;
import pickyeater.utils.MealQuantityConverter;
import pickyeater.utils.MouseClickListener;
import pickyeater.utils.StringToNumber;
import pickyeater.utils.StringsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealsChooser extends JDialog {
    private final MealChooserExecutor mealSearcherExecutor = ExecutorProvider.getMealChooserExecutor();
    private JTextField searchBar;
    private JList mealsList;
    private JLabel txtSearchMeal = new JLabel("Search Meals: ");
    private JPanel panelSearchBar = new JPanel();
    private List<Meal> searchedMeals;
    private final JButton cancelButton;
    private Meal returningMeal = null;
    private JPanel mealPanel = null;
    private final JTextField mealQuantityTextField = new JTextField();
    private final JPanel mealQuantityPanel = new JPanel(new GridBagLayout());
    private final JLabel mealQuantityTypeLabel = new JLabel("g");

    public MealsChooser(JFrame parent) {
        super(parent, "Meals Chooser", true);
        setLayout(new BorderLayout());
        panelSearchBar.setLayout(new BorderLayout());
        panelSearchBar.add(BorderLayout.WEST, txtSearchMeal);
        panelSearchBar.add(BorderLayout.CENTER, searchBar);
        add(BorderLayout.NORTH, panelSearchBar);
        JPanel ingredientListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        ingredientListPanel.add(new JScrollPane(mealsList), constraints);
        constraints.gridy = 1;
        JButton addMealButton = new JButton("Create meal");
        ingredientListPanel.add(addMealButton, constraints);
        add(BorderLayout.LINE_END, ingredientListPanel);
        mealQuantityTextField.setToolTipText("If left void it puts automatically 100g");
        addMealButton.addActionListener(l -> {
            MealCreator creator = new MealCreator(parent);
            creator.createMeal();
            searchedMeals = new ArrayList<>(mealSearcherExecutor.getEveryMeal());
            populateMealList();
            showPieChart();
        });
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = searchBar.getText();
                if(!StringsUtils.isAlpha(text)) searchedMeals = new ArrayList<>();
                else searchedMeals = new ArrayList<>(mealSearcherExecutor.getMealsThatStartWith(text));
                populateMealList();
            }
        });
        searchedMeals = new ArrayList<>(mealSearcherExecutor.getEveryMeal());
        populateMealList();
        mealsList.setMinimumSize(new Dimension(300, 300));
        mealsList.setToolTipText("Double click to check ingredients, right click to delete/edit meal");
        mealsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        mealsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                showPieChart();
            }
        });
        mealsList.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2)
                    return;
                int selectedIndex = mealsList.getSelectedIndex();
                if (selectedIndex < 0)
                    return;
                Meal meal = searchedMeals.get(selectedIndex);
                new MealInfoJDialog(parent, meal).run();
            }
        });
        if (!searchedMeals.isEmpty()) {
            showPieChart();
        } else {
            JOptionPane.showMessageDialog(parent, "Error 404, Meals not found", "Error 404", JOptionPane.ERROR_MESSAGE);
        }
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> {
            int selectedItem = mealsList.getSelectedIndex();
            if (selectedItem == -1) {
                dispose();
                return;
            }
            Meal meal = searchedMeals.get(selectedItem);
            MealQuantityConverter mealQuantityConverter = new MealQuantityConverter();
            int returningWeight;
            if (mealQuantityTextField.getText().isEmpty()) {
                returningWeight = 100;
            } else {
                returningWeight = StringToNumber.convertPositiveInteger(mealQuantityTextField.getText());
            }
            returningMeal = mealQuantityConverter.convert(meal, returningWeight);
            dispose();
        });
        mealsList.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Point framePoint = parent.getLocation();
                    Point realPoint = new Point(point.x - framePoint.x, point.y - framePoint.y);
                    int selectedIndex = mealsList.locationToIndex(e.getPoint());
                    mealsList.setSelectedIndex(selectedIndex);
                    if (selectedIndex < 0)
                        return;
                    Meal selectedMeal = searchedMeals.get(selectedIndex);
                    FoodPopupMenu popupMenu = new FoodPopupMenu();
                    popupMenu.addDeleteListener(l -> {
                        if (mealSearcherExecutor.isMealUsed(selectedMeal)) {
                            JOptionPane.showMessageDialog(parent, "Cannot delete this meal as it's being used!");
                            return;
                        }
                        int choice = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete it?");
                        if (choice != JOptionPane.YES_OPTION)
                            return;
                        mealSearcherExecutor.deleteMeal(selectedMeal);
                        searchedMeals.remove(selectedMeal);
                        populateMealList();
                        showPieChart();
                    });
                    popupMenu.addEditListener(l -> {
                        MealCreator creator = new MealCreator(parent);
                        creator.editMeal(selectedMeal);
                        String text = searchBar.getText();
                        searchedMeals = new ArrayList<>(mealSearcherExecutor.getMealsThatStartWith(text));
                        populateMealList();
                        showPieChart();
                    });
                    popupMenu.show(parent, realPoint.x, realPoint.y);
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

    private void populateMealList() {
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
        PieChart pieChart = new PieChart(300, 300);
        pieChart.setTitle(selectedMeal.getName());
        pieChart.addSeries("Proteins", mealNutrients.getProteins());
        pieChart.addSeries("Carbs", mealNutrients.getCarbs());
        pieChart.addSeries("Fats", mealNutrients.getFats());
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        XChartPanel<PieChart> chartPanel = new XChartPanel<>(pieChart);
        BorderLayout layout = (BorderLayout) getLayout();
        JPanel previousPanel = (JPanel) layout.getLayoutComponent(BorderLayout.LINE_START);
        if (previousPanel != null)
            remove(previousPanel);
        if (mealPanel != null)
            remove(mealPanel);
        mealPanel = new JPanel(new BorderLayout());
        mealPanel.add(BorderLayout.PAGE_START, chartPanel);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = 100;
        constraints.gridwidth = 1;
        mealQuantityPanel.add(mealQuantityTextField, constraints);
        constraints.gridx = 20;
        constraints.gridwidth = 1;
        mealQuantityPanel.add(mealQuantityTypeLabel, constraints);
        mealPanel.add(BorderLayout.PAGE_END, mealQuantityPanel);
        add(BorderLayout.LINE_START, mealPanel);
        revalidate();
    }

    public void manageMeals() {
        cancelButton.setVisible(false);
        mealQuantityPanel.setVisible(false);
        setVisible(true);
    }

    public Optional<Meal> getMeal() {
        setVisible(true);
        cancelButton.setVisible(true);
        mealQuantityPanel.setVisible(true);
        return Optional.ofNullable(returningMeal);
    }
}
