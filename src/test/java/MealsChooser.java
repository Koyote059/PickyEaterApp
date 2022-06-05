import org.junit.jupiter.api.Test;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.UI.pages.choosers.MealInfoJDialog;
import pickyeater.basics.food.Meal;
import pickyeater.basics.food.Nutrients;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.MealChooserExecutor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealsChooser extends JDialog {

    private JTextField searchBar;
    private JList mealsList;
    private JPanel mealPanel;
    private JLabel labelIcon;
    private List<Meal> searchedMeals;
    private final Meal returningMeal = null;
    private final MealChooserExecutor mealSearcherExecutor = ExecutorProvider.getMealChooserExecutor();

    public MealsChooser(Frame parent){
        super(parent,"Chose Meal",true);
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        setUpComponents(parent);


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.gridwidth=5;
        constraints.gridheight=5;
        layout.setConstraints(mealPanel,constraints);

        constraints.gridx=3;
        constraints.gridheight=1;
        constraints.gridwidth=1;
        layout.setConstraints(labelIcon,constraints);

        constraints.gridx=4;
        constraints.gridwidth=4;
        layout.setConstraints(searchBar,constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth=4;
        constraints.gridheight=5;
        layout.setConstraints(mealsList,constraints);
    }

    private void setUpComponents(Frame parent){
        mealPanel = new JPanel(new BorderLayout());

        mealsList = new JList();
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
                if(e.getClickCount()<2) return;
                int selectedIndex = mealsList.getSelectedIndex();
                if(selectedIndex<0) return;
                Meal meal = searchedMeals.get(selectedIndex);
                new MealInfoJDialog(parent,meal).run();
            }
        });

        searchBar = new JTextField();
        searchBar.addActionListener( l ->{
            String text = l.getActionCommand();
            searchedMeals = new ArrayList<>(mealSearcherExecutor.getMealsThatStartWith(text));
            populateMealList();
        });

        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/search_icon.png"));
            labelIcon.setIcon(new ImageIcon(binImage.getScaledInstance(20,20,Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            labelIcon = new JLabel("X");
        }
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
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        XChartPanel<PieChart> chartPanel = new XChartPanel<>(pieChart);
        BorderLayout layout = (BorderLayout) getLayout();
        JPanel previousPanel = (JPanel) layout.getLayoutComponent(BorderLayout.LINE_START);
        if(previousPanel!=null) remove(previousPanel);
        mealPanel.add(BorderLayout.PAGE_START,chartPanel);
        JTextField mealQuantityTextField = new JTextField("100");
        mealPanel.add(BorderLayout.PAGE_END, mealQuantityTextField);
        add(BorderLayout.LINE_START,mealPanel);
        revalidate();
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

    @Test
    public void test(){
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(new JPanel());
        jFrame.setVisible(true);
        new MealsChooser(jFrame).getMeal();
    }
}
