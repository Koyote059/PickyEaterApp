package pickyeater.utils.pagesutils;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.UI.themes.SystemTheme;
import pickyeater.executors.DailyProgressExecutor;
import pickyeater.executors.ExecutorProvider;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class NutrientsCategoryChart {
    private final XChartPanel<CategoryChart> chartPanel;

    public NutrientsCategoryChart() {
        DailyProgressExecutor dailyProgressExecutor = ExecutorProvider.getDailyProgressExecutor();
        List<Float> calories = new LinkedList<>(List.of(dailyProgressExecutor.getEatenCalories(), dailyProgressExecutor.getCaloriesToEat()));
        List<Float> proteins = new LinkedList<>(List.of(dailyProgressExecutor.getEatenProteins(), dailyProgressExecutor.getProteinsToEat()));
        List<Float> carbs = new LinkedList<>(List.of(dailyProgressExecutor.getEatenCarbs(), dailyProgressExecutor.getCarbsToEat()));
        List<Float> fats = new LinkedList<>(List.of(dailyProgressExecutor.getEatenFats(), dailyProgressExecutor.getFatsToEat()));
        CategoryChart chart = new CategoryChartBuilder().width(410).height(330).build();
        List<String> str = new LinkedList<>(List.of("Eaten", "To Eat"));
        chart.addSeries("Calories", str, calories);
        chart.addSeries("Proteins", str, proteins);
        chart.addSeries("Carbs", str, carbs);
        chart.addSeries("Fats", str, fats);
        styleCategoryChart(chart.getStyler());
        chartPanel = new XChartPanel<>(chart);
    }

    private void styleCategoryChart(CategoryStyler styler) {
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        Color[] colors = {Color.decode("#32AB5E"), Color.decode("#83D078"), Color.decode("#B1EA9D"), Color.decode("#CAFFB8")};
        styler.setSeriesColors(colors);
        styler.setChartBackgroundColor(SystemTheme.getPanelColor());
        styler.setLegendBackgroundColor(SystemTheme.getButtonColor());
        styler.setPlotBackgroundColor(SystemTheme.getPanelColor());
        styler.setLegendBorderColor(SystemTheme.getPanelColor());
        styler.setPlotBorderColor(SystemTheme.getPanelColor());
        styler.setLabelsFontColor(SystemTheme.getLabelColor());
        styler.setLabelsFont(SystemTheme.getFont());
        styler.setAxisTickLabelsColor(SystemTheme.getLabelColor());
        styler.setAxisTickLabelsFont(SystemTheme.getFont());
        styler.setChartTitleFont(SystemTheme.getFont());
        styler.setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        styler.setAvailableSpaceFill(.75);
        styler.setToolTipsEnabled(true);
        styler.setLegendVisible(true);
        styler.setPlotBorderVisible(true);
        styler.setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        styler.setStacked(false);
        styler.setOverlapped(false);
        styler.setXAxisTicksVisible(true);
        styler.setYAxisTicksVisible(true);
        styler.setToolTipsEnabled(true);
        styler.setPlotGridLinesColor(SystemTheme.getButtonColor());
    }

    public JPanel getPanel() {
        return chartPanel;
    }
}