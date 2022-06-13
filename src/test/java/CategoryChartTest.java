import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries;
import org.knowm.xchart.SwingWrapper;

import javax.swing.*;

public class CategoryChartTest extends JFrame {

    public static void main(String[] args) {
        int[] x = new int[]{0, 1};
        int[] a = new int[]{1, 3};
        int[] b = new int[]{2, 1};
        int[] c = new int[]{1, 1};
        int[] d = new int[]{2, 5};
        CategoryChart chart = new CategoryChartBuilder().width(640).height(480).build();
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        chart.getStyler().setAvailableSpaceFill(.25);
        chart.addSeries("Carbs", x, a);
        chart.addSeries("Fats", x, b);
        chart.addSeries("Proteins", x, c);
        chart.addSeries("Calories", x, d);


        chart.getStyler().setPlotBorderVisible(false);
        chart.getStyler().setStacked(false);
        chart.getStyler().setOverlapped(false);
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setYAxisTicksVisible(true);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setAxisTitlesVisible(false);


        new SwingWrapper(chart).displayChart();
    }

}
