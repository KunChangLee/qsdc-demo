package util;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public class Operators {

    private static final double SQURT2 = 1.0/Math.sqrt(2);

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




}
