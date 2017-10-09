package util;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public class Operators {

    private static final double SQURT2 = 1.0/Math.sqrt(2);
    private static double ALPHA = SQURT2;
    private static double BETA = SQURT2;
    private static double GAMA = SQURT2;
    private static double DELTA = SQURT2;

    public static void setAlphaAndGama(double alpha,double gama){
        ALPHA = alpha;
        BETA = 1.0 - alpha;
        GAMA =gama;
        DELTA = 1.0 - gama;
    }

    public static final double[][] Operator_I = new double[][]{
            {1, 0},
            {0, 1}
    };
    public static final double[][] Operator_X = new double[][]{
            {0, 1},
            {1, 0}
    };
    public static final double[][] Operator_iY = new double[][]{
            {0, 1},
            {-1, 0}
    };
    public static final double[][] Operator_Z = new double[][]{
            {1, 0},
            {0, -1}
    };
    public static final double[][] Operator_H = new double[][]{
            {SQURT2, SQURT2},
            {SQURT2, -SQURT2}
    };
    public static final double[][] Operator_U = new double[][]{
            {SQURT2,0,0, SQURT2},
            {0,SQURT2, SQURT2,0},
            {SQURT2,0,0, -SQURT2},
            {0,SQURT2, -SQURT2,0},
    };
    public static final double[][] Operator_E = new double[][]{
            {ALPHA,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,GAMA,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {BETA,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,DELTA,0,0,0},

    };




}
