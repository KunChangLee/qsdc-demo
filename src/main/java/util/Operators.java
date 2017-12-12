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
            {0,SQURT2, -SQURT2,0}
    };


    public static double[][] getOperator_E(double alpha, double gama){

        double beta = Math.sqrt(1.0 - alpha*alpha);
        double delta = Math.sqrt(1.0 - gama*gama);
        double[][] op = new double[][]{
                {alpha,0,0,0},
                {0,0, gama,0},
                {0,0,delta,0},
                {beta,0, 0,0},

        };
        return op;
    }
    public static double[][] getOperator_N(double cos){

        double sin = Math.sqrt(1.0 - cos*cos);
        double[][] op = new double[][]{
                {cos,-sin},
                {sin, cos},


        };
        return op;
    }
    public static double[][] getOperator_E2(double alpha, double gama){

        double beta = Math.sqrt(1.0 - alpha*alpha);
        double delta = Math.sqrt(1.0 - gama*gama);
        double[][] op = new double[][]{
                {alpha,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,gama,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {beta,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,delta,0,0,0},

        };
        return op;
    }

    public static double[][] Logic_I = Operation.operatorTensor(Operator_I,Operator_I);
    public static double[][] Logic_X = Operation.operatorTensor(Operator_Z,Operator_X);
    public static double[][] Logic_Y = Operation.operatorTensor(Operator_I,Operator_iY);
    public static double[][] Logic_Z = Operation.operatorTensor(Operator_Z,Operator_Z);
    public static double[][] Logic_H = new double[][]{
            {SQURT2,SQURT2,0,0},
            {0,0,SQURT2,SQURT2},
            {0,0,-SQURT2,-SQURT2},
            {SQURT2,SQURT2,0,0}
    };
    public static final double[][] Logic_U = new double[][]{
            {SQURT2,0,0,0,0, SQURT2,0,0,0,0,0,0,0,0,0,0},
            {0,SQURT2,0,0, SQURT2,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,SQURT2,0,0,0,0,0, SQURT2,0,0,0,0,0,0,0},
            {SQURT2,0,0,0,0,SQURT2,0,0,0,0,0,0,0,0,0,0},
            {SQURT2,0,0,0,0,0,SQURT2,0,0,0,0,0,0,0,0,0},
            {0,SQURT2,0,0,0,0,0,0,SQURT2,0,0,0,0,0,0,0},
            {0,0,SQURT2,0,SQURT2,0,0,0,0,0,0,0,0,0,0,0},
            {SQURT2,0,0,0,0,0,SQURT2,0,0,0,0,0,0,0,0,0},
            {-SQURT2,0,0,0,0,SQURT2,0,0,0,0,0,0,0,0,0,0},
            {0,0,SQURT2,0,SQURT2,0,0,0,0,0,0,0,0,0,0,0},
            {0,SQURT2,0,0,0,0,0,0,SQURT2,0,0,0,0,0,0,0},
            {-SQURT2,0,0,0,0,SQURT2,0,0,0,0,0,0,0,0,0,0},
            {SQURT2,0,0,0,0, SQURT2,0,0,0,0,0,0,0,0,0,0},
            {0,SQURT2,0,0, SQURT2,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,SQURT2,0,0,0,0,0, SQURT2,0,0,0,0,0,0,0},
            {SQURT2,0,0,0,0,SQURT2,0,0,0,0,0,0,0,0,0,0},
    };


}
