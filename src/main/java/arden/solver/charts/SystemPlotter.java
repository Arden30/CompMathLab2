package arden.solver.charts;

import arden.solver.system.systems.Function;
import arden.solver.system.systems.SystemOfEquations;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYZDataset;

import javax.swing.*;
import java.awt.*;

public class SystemPlotter extends JFrame {

    public SystemPlotter(SystemOfEquations systemOfEquations) {
        super("График системы уравнений");
        JPanel chartPanel = createChartPanel(systemOfEquations.first(), systemOfEquations.second());
        add(chartPanel, BorderLayout.CENTER);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel(Function function1, Function function2) {
        JFreeChart chart = createChart(function1, function2);
        return new ChartPanel(chart);
    }

    private JFreeChart createChart(Function function1, Function function2) {
        DefaultXYZDataset dataset = new DefaultXYZDataset();

        // Генерируем точки для первой функции
        double[][] data1 = generatePoints(function1);

        // Генерируем точки для второй функции
        double[][] data2 = generatePoints(function2);

        // Добавляем точки в набор данных
        dataset.addSeries("Function1", data1);
        dataset.addSeries("Function2", data2);

        // Создаем график
        JFreeChart chart = ChartFactory.createScatterPlot("График уравнений системы", "X", "Y", dataset);
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

        return chart;
    }

    private double[][] generatePoints(Function function) {
        // Создаем массивы для хранения значений x, y и соответствующих им z
        int numPoints = 100000; // Увеличим количество точек для большей плотности
        double[][] data = new double[3][numPoints];

        // Генерируем точки для функции
        int index = 0;
        for (double x = -10; x <= 10; x += 0.001) {
            for (double y = -10; y <= 10; y += 0.001) {
                double z = function.value(x, y); // значение функции в точке (x, y)
                if (Math.abs(z) < 0.0001) { // Проверяем, близко ли значение к нулю
                    data[0][index] = x;
                    data[1][index] = y;
                    data[2][index] = z;
                    index++;
                }
            }
        }

        // Уменьшаем размер массива, чтобы он соответствовал количеству реально сгенерированных точек
        double[][] trimmedData = new double[3][index];
        System.arraycopy(data[0], 0, trimmedData[0], 0, index);
        System.arraycopy(data[1], 0, trimmedData[1], 0, index);
        System.arraycopy(data[2], 0, trimmedData[2], 0, index);

        return trimmedData;
    }
}
