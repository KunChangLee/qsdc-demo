package util;

import quantum.QuantumState;

/**
 * Created by Zhao Zhe on 2017/9/12.
 */
public class Measurement {

    public static int measureBaseZ(QuantumState state, int pos){
        double[] states = state.getState();

        double zeroProb = 0.0;
        double oneProb = 0.0;
        for (int i = 0; i < states.length; i++) {
            if(isBitOne(i,pos,state.getParticles())){
                oneProb += Math.pow(states[i],2);
            }else {
                zeroProb += Math.pow(states[i],2);
            }

        }
        double random = Math.random();
        int result = 0;
        if(random < zeroProb)
            result = 0;
        else
            result = 1;

        if(result == 0){
            for (int i = 0; i < states.length; i++) {
                if(isBitOne(i,pos,state.getParticles())){
                    states[i] = 0;
                }

            }
        }else {
            for (int i = 0; i < states.length; i++) {
                if(isBitZero(i,pos,state.getParticles())){
                    states[i] = 0;
                }

            }
        }


        Operation.normalization(states);

        return result;
    }

    public static int measureBaseX(QuantumState state, int pos){
        double[][] temp = Operation.performOperator(state,pos,Operators.Operator_H);
        double[] st = Operation.vecToArray(temp);
        state.setState(st);
        return measureBaseZ(state,pos);
    }

    /*
        1-->phi+
        2-->psi+
        3-->phi-
        4-->psi-
     */
    public static int measureBaseBell(QuantumState state){
        double[][] temp = Operation.operatorTensor(Operators.Operator_I,Operators.Operator_I);
        temp = Operation.operatorTensor(temp,Operators.Operator_U);
        double[] st = state.getState();
        temp = Operation.innerProduct(temp,Operation.transposition(st));
        st = Operation.vecToArray(temp);
        state.setState(st);

        int p3 = measureBaseZ(state,3);
        int p4 = measureBaseZ(state,4);

        int result = 0;

        if(p3 == 0){
            if(p4 == 0)
                result = 1;
            else
                result = 2;
        }else {
            if(p4 == 0)
                result = 3;
            else
                result = 4;
        }

        return result;
    }

    private static boolean isBitZero(int num, int pos, int par){
        int len = par - pos;
        int temp = 1 << len;
        if((temp & num) == 0)
            return true;
        else
            return false;
    }
    private static boolean isBitOne(int num, int pos, int par){
        int len = par - pos;
        int temp = 1 << len;
        if((temp & num) == temp)
            return true;
        else
            return false;
    }
}
