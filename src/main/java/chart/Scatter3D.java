package chart;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhao Zhe on 2017/12/12.
 */
public class Scatter3D extends AbstractAnalysis {
    private List<Double> error;
    private List<Double> alpha;
    private List<Double> gama;
    public Scatter3D(Map<String,List<Double>> map){
        error = map.get("error");
        alpha = map.get("fac1");
        gama = map.get("fac2");
    }
    public Scatter3D(){}

    public static void draw(Map<String,List<Double>> map) throws Exception{

        AnalysisLauncher.open(new Scatter3D(map));


    }

    public void init() {
        int size = error.size();
        float x;
        float y;
        float z;
        float a;

        Coord3d[] points = new Coord3d[size];
        Color[]   colors = new Color[size];

        System.out.println(error.size());
        System.out.println(alpha.size());
        System.out.println(gama.size());



        for(int i=0; i<size; i++){
            x = new Double(alpha.get(i)).floatValue();
            y = new Double(gama.get(i)).floatValue();
            z = new Double(error.get(i)).floatValue();
            //System.out.println(x + ", " + y + "," + z);
            points[i] = new Coord3d(x, y, z);
            a = 0.25f;
            colors[i] = new Color(x, y, z);
        }

        Scatter scatter = new Scatter(points, colors);
        chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");
        chart.getScene().add(scatter);
    }

}
