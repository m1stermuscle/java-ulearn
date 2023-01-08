import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class TimeSeriesChartDrawer extends JFrame {
    public TimeSeriesChartDrawer(String title, Map<Integer, Integer> data) {
        super(title);
        setContentPane(createPlayersByTeamsPanel(data));
        //XYDataset dataset = CreateDataset(data);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(900, 400);
    }

    public static XYDataset CreateDataset(Map<Integer, Integer> data) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("Землетрясения");
        for (Map.Entry<Integer, Integer> entry: data.entrySet()) {
            //System.out.println(entry.getKey() + ", " + entry.getValue());
            series.add(new Year(entry.getKey()), entry.getValue());
        }
        dataset.addSeries(series);
        return dataset;
    }

    public static JPanel createPlayersByTeamsPanel(Map<Integer, Integer> data)
    {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Землетрясения по годам", // Chart
                "Год", // X-Axis Label
                "Количество", // Y-Axis Label
                CreateDataset(data));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        return new ChartPanel(chart);
    }
}

