package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.Map;
import java.util.List;

/**
 * Created by Zhao Zhe on 2017/12/11.
 */
public class TimeSeriesChart {
    public TimeSeriesChart(Map<String,List<Double>> map){
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        XYSeriesCollection mCollection = GetCollection(map);
        JFreeChart mChart = ChartFactory.createXYLineChart(
                "折线图",
                "Cos",
                "信道误码率",
                mCollection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);
    }
    public static XYSeriesCollection GetCollection(Map<String, List<Double>> map)
    {
        XYSeriesCollection mCollection = new XYSeriesCollection();
        XYSeries mSeriesFirst = new XYSeries("GHZ");
        XYSeries mSeriesSecond = new XYSeries("W");
        List<Double> data = map.get("data");
        List<Double> result1 = map.get("result1");
        List<Double> result2 = map.get("result2");

        for (int i = 0; i < data.size(); i++) {
            double x = data.get(i);
            double y1 = result1.get(i);
            double y2 = result2.get(i);
            System.out.println(y1);
            System.out.println(y2);

            mSeriesFirst.add(x,y1);
            mSeriesSecond.add(x,y2);
        }
        mCollection.addSeries(mSeriesFirst);
        mCollection.addSeries(mSeriesSecond);
        return mCollection;
    }

}
