package pickyeater.UI.choosers;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.searcher.IngredientSearcherExecutor;
import pickyeater.utils.IngredientQuantityConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IngredientChooser extends JDialog {
    private JTextField searchBar;
    private JList ingredientsList;
    private List<Ingredient> searchedIngredients;

    private Ingredient returningIngredient = null;
    private JTextField mealQuantityTextField;
    private final IngredientSearcherExecutor ingredientsSearcherExecutor = ExecutorProvider.getIngredientSearcherExecutor();

    public IngredientChooser(Frame parent) {
        super(parent,"Ingredient Chooser",true);
        add(new JPanel());
        searchBar = new JTextField();
        ingredientsList = new JList();
        setLayout(new BorderLayout());
        add(BorderLayout.PAGE_START,searchBar);
        add(BorderLayout.LINE_END, ingredientsList);
        searchBar.addActionListener( l ->{
            String text = l.getActionCommand();
            searchedIngredients = new ArrayList<>(ingredientsSearcherExecutor.getIngredientsThatStartWith(text));
            populateIngredientsList();
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
        showPieChart();
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( e -> dispose());
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener( e ->{
            int selectedItem = ingredientsList.getSelectedIndex();
            Ingredient ingredient = searchedIngredients.get(selectedItem);
            IngredientQuantityConverter ingredientQuantityConverter = new IngredientQuantityConverter();
            String quantity = mealQuantityTextField.getText();
            try {
                int returningWeight = Integer.parseInt(quantity);
                returningIngredient = ingredientQuantityConverter.convert(ingredient,returningWeight);
                dispose();
            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(this,"Invalid meal quantity!");
            }
        });
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(doneButton);
        add(BorderLayout.PAGE_END,buttonsPanel);

        setSize(new Dimension(455,410));
        setResizable(false);

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

    public Optional<Ingredient> getIngredient(){
        setVisible(true);
        return Optional.ofNullable(returningIngredient);
    }

    private void showPieChart(){
        int selectedItem = ingredientsList.getSelectedIndex();
        Ingredient selectedIngredient = searchedIngredients.get(selectedItem);
        Nutrients ingredientNutrients = selectedIngredient.getNutrients();
        PieChart pieChart = new PieChart(300,300);
        pieChart.setTitle(selectedIngredient.getName());
        pieChart.addSeries("Proteins",ingredientNutrients.getProteins());
        pieChart.addSeries("Carbs",ingredientNutrients.getCarbs());
        pieChart.addSeries("Fats",ingredientNutrients.getFats());
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
