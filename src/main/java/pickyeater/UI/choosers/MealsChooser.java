package pickyeater.UI.choosers;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealChooserExecutor;
import pickyeater.utils.MealQuantityConverter;

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

    private Meal returningMeal = null;
    private JTextField mealQuantityTextField;
    private final MealChooserExecutor mealSearcherExecutor = ExecutorProvider.getMealChooserExecutor();

    public MealsChooser(Frame parent) {
        super(parent,"Meals Chooser",true);
        setLayout(new BorderLayout());
        add(BorderLayout.PAGE_START,searchBar);
        add(BorderLayout.LINE_END,mealsList);
        searchBar.addActionListener( l ->{
            String text = l.getActionCommand();
            searchedMeals = new ArrayList<>(mealSearcherExecutor.getMealsThatStartWith(text));
            populateMealList();
        });
        searchedMeals = new ArrayList<>(mealSearcherExecutor.getEveryMeal());
        populateMealList();
        mealsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        mealsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                showPieChart();
            }
        });
        showPieChart();
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( e -> dispose());
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener( e ->{
            int selectedItem = mealsList.getSelectedIndex();
            Meal meal = searchedMeals.get(selectedItem);
            MealQuantityConverter mealQuantityConverter = new MealQuantityConverter();
            String quantity = mealQuantityTextField.getText();
            try {
                int returningWeight = Integer.parseInt(quantity); // Todo :C
                returningMeal = mealQuantityConverter.convert(meal,returningWeight);
                dispose();
            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(this,"Invalid meal quantity!");
            }
        });
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(doneButton);
        add(BorderLayout.PAGE_END,buttonsPanel);

        setSize(new Dimension(500,500));

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

    public Optional<Meal> getMeal(){
        setVisible(true);
        return Optional.ofNullable(returningMeal);
    }

    private void showPieChart(){
        int selectedItem = mealsList.getSelectedIndex();
        Meal selectedMeal = searchedMeals.get(selectedItem);
        Nutrients mealNutrients = selectedMeal.getNutrients();
        PieChart pieChart = new PieChart(300,300);
        pieChart.setTitle(selectedMeal.getName());
        pieChart.addSeries("Proteins",mealNutrients.getProteins());
        pieChart.addSeries("Carbs",mealNutrients.getCarbs());
        pieChart.addSeries("Fats",mealNutrients.getFats());
        JPanel mealPanel = new JPanel(new BorderLayout());
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        XChartPanel<PieChart> chartPanel = new XChartPanel<>(pieChart);
        BorderLayout layout = (BorderLayout) getLayout();
        JPanel previousPanel = (JPanel) layout.getLayoutComponent(BorderLayout.LINE_START);
        if(previousPanel!=null) remove(previousPanel);
        mealPanel.add(BorderLayout.PAGE_START,chartPanel);
        mealQuantityTextField = new JTextField("100");
        mealPanel.add(BorderLayout.PAGE_END,mealQuantityTextField);
        add(BorderLayout.LINE_START,mealPanel);
        revalidate();
    }
}
