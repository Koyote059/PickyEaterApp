package pickyeater.UI.pages.utils;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import pickyeater.basics.food.Nutrients;

import javax.swing.*;
import java.awt.*;

public class NutrientsPieChart {

    private final XChartPanel<PieChart> chartPanel;

    public NutrientsPieChart(Nutrients nutrients, String name) {
        PieChart pieChart = new PieChart(410, 330);
        pieChart.setTitle(name);
        pieChart.addSeries("Proteins", nutrients.getProteins());
        pieChart.addSeries("Carbs", nutrients.getCarbs());
        pieChart.addSeries("Fats", nutrients.getFats());
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        Color[] colors = {Color.decode("#32AB5E"), Color.decode("#83D078"), Color.decode("#B1EA9D")};
        styler.setSeriesColors(colors);
        chartPanel = new XChartPanel<>(pieChart);
    }

    public NutrientsPieChart() {
        PieChart pieChart = new PieChart(410, 330);
        pieChart.setTitle("");
        pieChart.addSeries("Proteins", 0);
        pieChart.addSeries("Carbs", 0);
        pieChart.addSeries("Fats", 0);
        PieStyler styler = pieChart.getStyler();
        styler.setToolTipType(Styler.ToolTipType.yLabels);
        styler.setToolTipsEnabled(true);
        Color[] colors = {Color.decode("#32AB5E"), Color.decode("#83D078"), Color.decode("#B1EA9D")};
        styler.setSeriesColors(colors);
        chartPanel = new XChartPanel<>(pieChart);
    }

    public JPanel getPanel(){
        return chartPanel;
    }

    public void setNutrients(Nutrients nutrients){
        PieChart pieChart = chartPanel.getChart();
        pieChart.updatePieSeries("Proteins",nutrients.getProteins());
        pieChart.updatePieSeries("Carbs", nutrients.getCarbs());
        pieChart.updatePieSeries("Fats", nutrients.getFats());
        chartPanel.repaint();
    }

    public void setName(String name){
        PieChart pieChart = chartPanel.getChart();
        pieChart.setTitle(name);
        chartPanel.repaint();
    }
}
