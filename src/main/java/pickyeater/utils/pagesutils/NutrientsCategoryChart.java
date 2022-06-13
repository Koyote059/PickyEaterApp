package pickyeater.utils.pagesutils;

import org.knowm.xchart.*;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.UI.themes.SystemTheme;
import pickyeater.basics.food.Nutrients;
import pickyeater.executors.DailyProgressExecutor;
import pickyeater.executors.ExecutorProvider;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class NutrientsCategoryChart {

    private final XChartPanel<CategoryChart> chartPanel;

    public NutrientsCategoryChart(Nutrients nutrients, Nutrients userNutrients) {
        CategoryChart categoryChart = new CategoryChart(410, 330);
        categoryChart.setTitle("");
        int[] cal = new int[]{(int)nutrients.getCalories(), (int)userNutrients.getCalories()};
        categoryChart.addSeries("Calories", cal, cal);

        int[] prot = new int[]{(int)nutrients.getProteins(), (int)userNutrients.getProteins()};
        categoryChart.addSeries("Proteins", prot, prot);

        int[] carbs = new int[]{(int)nutrients.getCarbs(), (int)userNutrients.getCarbs()};
        categoryChart.addSeries("Carbs", carbs, carbs);

        int[] fats = new int[]{(int)nutrients.getFats(), (int)userNutrients.getFats()};
        categoryChart.addSeries("Fats", fats, fats);
        styleCategoryChart(categoryChart.getStyler());
        chartPanel = new XChartPanel<>(categoryChart);
    }
/*

        dailyProgressExecutor.getEatenProteins();
        dailyProgressExecutor.getProteinsToEat();

        dailyProgressExecutor.getEatenCarbs();
        dailyProgressExecutor.getCarbsToEat();

        dailyProgressExecutor.getEatenFats();
        dailyProgressExecutor.getFatsToEat();
 */
    public NutrientsCategoryChart() {
        DailyProgressExecutor dailyProgressExecutor = ExecutorProvider.getDailyProgressExecutor();
        List<Float> calories = new LinkedList<>(List.of( dailyProgressExecutor.getEatenCalories(),
                dailyProgressExecutor.getCaloriesToEat()));

        List<Float> proteins = new LinkedList<>(List.of(dailyProgressExecutor.getEatenProteins(),
                dailyProgressExecutor.getProteinsToEat()));

        List<Float> carbs = new LinkedList<>(List.of(dailyProgressExecutor.getEatenCarbs(),
                dailyProgressExecutor.getCarbsToEat()));

        List<Float> fats = new LinkedList<>(List.of(dailyProgressExecutor.getEatenFats(),
                dailyProgressExecutor.getFatsToEat()));

        CategoryChart chart = new CategoryChartBuilder().width(410).height(330).build();
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        chart.getStyler().setAvailableSpaceFill(.25);

        List<String> str = new LinkedList<>(List.of("Eaten", "To Eat"));

        chart.addSeries("Calories", str, calories);
        chart.addSeries("Proteins", str, proteins);
        chart.addSeries("Carbs", str, carbs);
        chart.addSeries("Fats", str, fats);
        styleCategoryChart(chart.getStyler());
        chartPanel = new XChartPanel<>(chart);
    }

    public JPanel getPanel(){
        return chartPanel;
    }

    private void styleCategoryChart(CategoryStyler styler){
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        Color[] colors = {Color.decode("#32AB5E"), Color.decode("#83D078"), Color.decode("#B1EA9D"), Color.decode("#CAFFB8")};
        styler.setSeriesColors(colors);
        styler.setChartBackgroundColor(SystemTheme.getPanelColor());
        styler.setLegendBackgroundColor(SystemTheme.getButtonColor());
        styler.setPlotBackgroundColor(SystemTheme.getPanelColor());
        styler.setLegendBorderColor(SystemTheme.getPanelColor());
        styler.setPlotBorderColor(SystemTheme.getPanelColor());
        styler.setLabelsFontColor(SystemTheme.getLabelColor());
        styler.setLabelsFont(SystemTheme.getFont());
        styler.setChartTitleFont(SystemTheme.getFont());

        styler.setPlotBorderVisible(true);
        styler.setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        styler.setStacked(false);
        styler.setOverlapped(false);
        styler.setLegendVisible(true);
        styler.setXAxisTicksVisible(true);
        styler.setYAxisTicksVisible(true);
        styler.setToolTipsEnabled(true);
        styler.setLabelsFontColorAutomaticEnabled(true);
        styler.setPlotGridLinesColor(ColorButtons.getWhite());
        //styler.setLabelsFontColor(ColorButtons.getWhite());
    }
}