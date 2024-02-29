package arden.solver.charts;

import arden.solver.equation.functions.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

public class EquationPlotter extends JFrame {

    public EquationPlotter(Function equation) {
        super("График уравнения");
        JPanel chartPanel = createChartPanel(equation);
        add(chartPanel, BorderLayout.CENTER);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel(Function equation) {
        String chartTitle = "График уравнения";
        String xAxisLabel = "X";
        String yAxisLabel = "Y";

        XYDataset dataset = createDataset(equation);
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
        chart.getXYPlot().getDomainAxis().setRange(-10, 10);
        chart.getXYPlot().getRangeAxis().setRange(-10, 10);

        NumberAxis xAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();

        xAxis.setTickUnit(new NumberTickUnit(1));
        yAxis.setTickUnit(new NumberTickUnit(1));

        // Настройка стилей линий осей координат
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setDomainZeroBaselinePaint(Color.BLACK); // черный цвет для вертикальной оси 0
        plot.setRangeZeroBaselinePaint(Color.BLACK); // черный цвет для горизонтальной оси 0

        // Устанавливаем толщину линии для функций
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setStroke(new BasicStroke(3)); // Установите желаемую толщину линии здесь (2 - это пример)

        return new ChartPanel(chart);
    }

    private XYDataset createDataset(Function equation) {
        Function2D function = equation::value;

        return DatasetUtilities.sampleFunction2D(function, -10.0, 10.0, 1000, equation.function());
    }

}
