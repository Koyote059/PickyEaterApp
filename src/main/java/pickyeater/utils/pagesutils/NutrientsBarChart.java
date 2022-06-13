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

public class NutrientsBarChart {

    private final XChartPanel<CategoryChart> chartPanel;

    public NutrientsBarChart(Nutrients nutrients, Nutrients userNutrients) {
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
    public NutrientsBarChart() {
        double[] x = new double[]{0, 1};
        DailyProgressExecutor dailyProgressExecutor = ExecutorProvider.getDailyProgressExecutor();
        double[] cal = new double[]{dailyProgressExecutor.getEatenCalories(), dailyProgressExecutor.getCaloriesToEat()};
        double[] proteins = new double[]{dailyProgressExecutor.getEatenProteins(), dailyProgressExecutor.getProteinsToEat()};
        double[] carbs = new double[]{dailyProgressExecutor.getEatenCarbs(), dailyProgressExecutor.getCarbsToEat()};
        double[] fats = new double[]{dailyProgressExecutor.getEatenFats(), dailyProgressExecutor.getFatsToEat()};

        CategoryChart chart = new CategoryChartBuilder().width(410).height(330).build();
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        chart.getStyler().setAvailableSpaceFill(.25);
        chart.addSeries("Calories", x, cal);
        chart.addSeries("Proteins", x, proteins);
        chart.addSeries("Carbs", x, carbs);
        chart.addSeries("Fats", x, fats);

        styleCategoryChart(chart.getStyler());
        chartPanel = new XChartPanel<>(chart);
    }

    public JPanel getPanel(){
        return chartPanel;
    }

    private void styleCategoryChart(CategoryStyler styler){
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        Color[] colors = {Color.decode("#32AB5E"), Color.decode("#83D078"), Color.decode("#B1EA9D")};
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
        styler.setToolTipsEnabled(false);
        styler.setAxisTitlesVisible(false);
        styler.setLabelsFontColorAutomaticEnabled(true);
        //styler.setPlotGridLinesVisible(false);
        styler.setPlotGridLinesColor(ColorButtons.getWhite());
    }
}