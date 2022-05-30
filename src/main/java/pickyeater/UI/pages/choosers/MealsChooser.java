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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealsChooser extends JDialog {
    private JTextField searchBar;
    private JList mealsList;
    private List<Meal> searchedMeals;
    private JButton cancelButton;
    private Meal returningMeal = null;
    private JPanel mealPanel = null;
    private JTextField mealQuantityTextField = new JTextField("100");
    private final MealChooserExecutor mealSearcherExecutor = ExecutorProvider.getMealChooserExecutor();

    public MealsChooser(JFrame parent) {
        super(parent,"Meals Chooser",true);
        setLayout(new BorderLayout());
        add(BorderLayout.PAGE_START,searchBar);
        JPanel ingredientListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        ingredientListPanel.add(new JScrollPane(mealsList),constraints);
        constraints.gridy = 1;
        JButton addMealButton = new JButton("Create meal");
        ingredientListPanel.add(addMealButton,constraints);
        add(BorderLayout.LINE_END,ingredientListPanel);

        addMealButton.addActionListener( l -> {
            MealCreator creator = new MealCreator(parent);
            creator.createMeal();
            searchedMeals = new ArrayList<>(mealSearcherExecutor.getEveryMeal());
            populateMealList();
            showPieChart();
        });

        searchBar.addActionListener( l ->{
            String text = l.getActionCommand();
            searchedMeals = new ArrayList<>(mealSearcherExecutor.getMealsThatStartWith(text));
            populateMealList();
        });
        searchedMeals = new ArrayList<>(mealSearcherExecutor.getEveryMeal());
        populateMealList();
        mealsList.setMinimumSize(new Dimension(300,300));
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
                if(e.getClickCount()<2) return;
                int selectedIndex = mealsList.getSelectedIndex();
                if(selectedIndex<0) return;
                Meal meal = searchedMeals.get(selectedIndex);
                new MealInfoJDialog(parent,meal).run();
            }
        });
        showPieChart();
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( e -> dispose());
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener( e ->{
            int selectedItem = mealsList.getSelectedIndex();
            if(selectedItem==-1){
                dispose();
                return;
            }
            Meal meal = searchedMeals.get(selectedItem);
            MealQuantityConverter mealQuantityConverter = new MealQuantityConverter();
            String quantity = mealQuantityTextField.getText();
            try {
                int returningWeight = Integer.parseInt(quantity);
                returningMeal = mealQuantityConverter.convert(meal,returningWeight);
                dispose();
            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(this,"Invalid meal quantity!");
            }
        });

        mealsList.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    Point framePoint = parent.getLocation();
                    Point realPoint = new Point(point.x - framePoint.x,point.y - framePoint.y);
                    int selectedIndex = mealsList.locationToIndex(e.getPoint());
                    mealsList.setSelectedIndex(selectedIndex);
                    if(selectedIndex<0) return;
                    Meal selectedMeal = searchedMeals.get(selectedIndex);
                    FoodPopupMenu popupMenu = new FoodPopupMenu();
                    popupMenu.addDeleteListener(l -> {
                        if(mealSearcherExecutor.isMealUsed(selectedMeal)){
                            JOptionPane.showMessageDialog(parent,"Cannot delete this meal as it's being used!");
                            return;
                        }
                        int choice = JOptionPane.showConfirmDialog(parent,"Are you sure you want to delete it?");
                        if(choice != JOptionPane.YES_OPTION) return;
                        mealSearcherExecutor.deleteMeal(selectedMeal);
                        searchedMeals.remove(selectedMeal);
                        populateMealList();
                        showPieChart();
                    });

                    popupMenu.addEditListener( l -> {
                        MealCreator creator = new MealCreator(parent);
                        creator.editMeal(selectedMeal);
                        String text = searchBar.getText();
                        searchedMeals = new ArrayList<>(mealSearcherExecutor.getMealsThatStartWith(text));
                        populateMealList();
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

    private void populateMealList(){
        Object[] listData = new Object[searchedMeals.size()];
        for (int i = 0; i < searchedMeals.size(); i++) {
            Meal meal = searchedMeals.get(i);
            listData[i] = meal.getName();
        }
        mealsList.setListData(listData);
        if(searchedMeals.size()!=0) {
            mealsList.setSelectedIndex(0);
        }
        repaint();

    }

    private void showPieChart(){
        int selectedItem = mealsList.getSelectedIndex();
        if(selectedItem==-1) return;
        Meal selectedMeal = searchedMeals.get(selectedItem);
        Nutrients mealNutrients = selectedMeal.getNutrients();
        PieChart pieChart = new PieChart(300,300);
        pieChart.setTitle(selectedMeal.getName());
        pieChart.addSeries("Proteins",mealNutrients.getProteins());
        pieChart.addSeries("Carbs",mealNutrients.getCarbs());
        pieChart.addSeries("Fats",mealNutrients.getFats());
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        XChartPanel<PieChart> chartPanel = new XChartPanel<>(pieChart);
        BorderLayout layout = (BorderLayout) getLayout();
        JPanel previousPanel = (JPanel) layout.getLayoutComponent(BorderLayout.LINE_START);
        if(previousPanel!=null) remove(previousPanel);
        mealQuantityTextField.setText("100");
        if(mealPanel!=null) remove(mealPanel);
        mealPanel = new JPanel(new BorderLayout());
        mealPanel.add(BorderLayout.PAGE_START,chartPanel);
        mealPanel.add(BorderLayout.PAGE_END,mealQuantityTextField);
        add(BorderLayout.LINE_START,mealPanel);
        revalidate();
    }

    public void manageMeals() {
        cancelButton.setVisible(false);
        mealQuantityTextField.setVisible(false);
        setVisible(true);
    }

    public Optional<Meal> getMeal(){
        setVisible(true);
        cancelButton.setVisible(true);
        mealQuantityTextField.setVisible(true);
        return Optional.ofNullable(returningMeal);
    }
}
