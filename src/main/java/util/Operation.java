package util;

import quantum.QuantumState;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public class Operation {

    public static double[][] operatorTensor(double[][] operator1,double[][] operator2){
        int row1 = operator1.length;
        int row2 = operator2.length;
        int col1 = operator1[0].length;
        int col2 = operator2[0].length;
        int row = row1*row2;
        int col = col1*col2;

        double[][] result = new double[row][col];

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col1; j++) {
                for (int k = 0; k < row2; k++) {
                    for (int l = 0; l < col2; l++) {
                        result[i*row2+k][j*col2+l] = operator1[i][j]*operator2[k][l];
                    }
                }
            }

        }
        return result;
    }

    public static double[][] innerProduct(double[][] matrix1, double[][] matrix2){
        int row1 = matrix1.length;
        int col1 = matrix1[0].length;
        int row2 = matrix2.length;
        int col2 = matrix2[0].length;
        double[][] result = new double[row2][col2];

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {

                double sum = 0.0;
                for (int k = 0; k < col1; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;

            }
        }

        return result;
    }

    public static double[][] performOperator(QuantumState state, int pos, double[][] operator){
        double[][] targetState = transposition(state.getState());
        double[][] temp = null;
        if(pos == 1)
            temp = operator;
        else
            temp = Operators.Operator_I;
        for (int i = 2; i <= state.getParticles(); i++) {

            if(i != pos){
                temp = Operation.operatorTensor(temp,Operators.Operator_I);
            }else {
                temp = Operation.operatorTensor(temp,operator);
            }
        }

        return innerProduct(temp,targetState);

    }


    public static void normalization(double[] matrix){
        double sum = 0.0;
        int len = matrix.length;
        for (int i = 0; i < len; i++) {
            sum += Math.pow(matrix[i],2);
        }

        sum = Math.sqrt(sum);

        for (int i = 0; i < len; i++) {
            if(!(Math.abs(matrix[i]) < 0.00000000000001)){
                matrix[i] = matrix[i]/sum;
            }
        }


    }

    public static double[][] transposition(double[] state){
        int len = state.length;
        double[][] result = new double[len][1];
        for (int i = 0; i < len; i++) {
            result[i][0] = state[i];
        }

        return result;
    }

    public static double[] vecToArray(double[][] vec){
        double[] result = new double[vec.length];
        for (int i = 0; i < vec.length; i++) {
            result[i] = vec[i][0];
        }
        return result;
    }


}
