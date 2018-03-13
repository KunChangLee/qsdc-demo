package chart;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

/**
 * Created by Zhao Zhe on 2017/12/15.
 */
public class Surface3D extends AbstractAnalysis {

    public static void draw() throws Exception{

        AnalysisLauncher.open(new Surface3D());


    }

    public void init() {
        // Define a function to plot
        Mapper mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                //return 0.5 - x/4 + y/4;
                //double i = 1-x*x;
                //double j = 1-y*y;
                //return 1 - (Math.pow(x,3)+Math.pow(y,3)+Math.pow(1-x,3)+Math.pow(1-y,3))/2;
                //return 1-(x*x-3*x*x*y+2*x*y);
                //double i = Math.pow(x,4);
                //double j = y*y;
                //return 1-(i-i*j*3+2*x*x*j);
                //double a = Math.pow(x,4);
                //double g = Math.pow(y,4);
                //double b = Math.pow(1-x*x,2);
                //double d = Math.pow(1-y*y,2);
                //
                //double part1 = (a*a+b*b+g*g+d*d)/4;
                //double part2 = (a*b+a*g+a*d+b*g+b*d+g*d)/2;
                //
                //return  1-part1-part2;

                double part1 = Math.pow(x,4)+Math.pow(y,4);
                double part2 = 2*(Math.pow(x,3)+Math.pow(y,3));
                double part3 = 3*(Math.pow(x,2)+Math.pow(y,2));
                double part4 = 2*(x+y);
                double part5 = 2*(x*x*y*y-x*y*y-x*x*y+x*y);
                return -(part1-part2+part3-part4+part5);
            }
        };

        // Define range and precision for the function to plot
        Range range = new Range(0, 1);
        int steps = 80;

        // Create the object to represent the function over the given range.
        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        // Create a chart
        chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
        chart.getScene().getGraph().add(surface);
    }
}
