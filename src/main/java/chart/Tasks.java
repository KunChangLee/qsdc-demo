package chart;

import quantum.QuantumState;
import quantum.impl.ClusterState;
import quantum.impl.ComputaionState;
import quantum.impl.EaveState;
import quantum.impl.GHZState;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Zhao Zhe on 2017/12/19.
 */
public class Tasks implements Callable<Double> {
    private double alpha=0;
    private double gama=0;

    public Tasks(double alpha, double gama){
        this.alpha = alpha;
        this.gama = gama;
    }

    public Double call() throws Exception {
        long start = System.currentTimeMillis();
        Map<String,List> payload = prepareW(alpha,gama,100);
        double result =  calcW(payload);
        long end = System.currentTimeMillis();
        System.out.println("time:" + (end-start));
        return result;
    }

    public static Map<String,List> prepareW(double alpha, double gama, int num){

        Map<String,List> payload = new HashMap<String, List>();
        List<QuantumState> sequence = new ArrayList<QuantumState>();
        QuantumState eave = new EaveState();
        QuantumState state0 = new ComputaionState(0);
        QuantumState state1 = new ComputaionState(1);
        alpha = Math.sqrt(alpha);
        gama = Math.sqrt(gama);
        for (int i = 0; i < num; i++) {

            double[][] eaveState = Operation.transposition(eave.getState());



            double[][] state00 = Operation.transposition(state0.getState());
            double[][] state11 = Operation.transposition(state1.getState());


            double[][] temp = Operation.operatorTensor(state00,eaveState);
            double[][] temp1 = Operation.operatorTensor(state11,eaveState);

            double[][] temp0_1 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
            double[][] temp0_2 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
            double[][] temp0_3 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
            double[][] temp0_4 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
            double[][] temp0_5 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
            double[][] temp0_6 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
            double[][] temp0_7 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
            double[][] temp0_8 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
            double[][] temp1_1 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
            double[][] temp1_2 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
            double[][] temp1_3 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
            double[][] temp1_4 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
            double[][] temp1_5 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
            double[][] temp1_6 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
            double[][] temp1_7 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
            double[][] temp1_8 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);

            double[][] part1 = Operation.operatorTensor(Operation.operatorTensor(Operation.operatorTensor(temp0_1,temp0_2),temp0_3),temp0_4);
            double[][] part2 = Operation.operatorTensor(Operation.operatorTensor(Operation.operatorTensor(temp0_5,temp0_6),temp1_1),temp1_2);
            double[][] part3 = Operation.operatorTensor(Operation.operatorTensor(Operation.operatorTensor(temp1_3,temp1_4),temp0_7),temp0_8);
            double[][] part4 = Operation.operatorTensor(Operation.operatorTensor(Operation.operatorTensor(temp1_5,temp1_6),temp1_7),temp1_8);

            part1 = Operation.add(part1,part2);
            part1 = Operation.add(part1,part3);
            part1 = Operation.sub(part1,part4);
            part1 = Operation.multiple(0.5,part1);

            QuantumState target = new ClusterState();
            target.setParticles(12);
            target.setState(Operation.vecToArray(part1));
            sequence.add(target);



        }
        payload.put(Payload.SEQUENCE,sequence);
        return payload;

    }
    private static double calcW(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        int total = sequence.size();
        int sum = 0;
        for (int i = 0; i < total; i++) {
            QuantumState state = sequence.get(i);
            int f0_1 = Measurement.measureBaseZ(state,1);
            int f0_2 = Measurement.measureBaseZ(state,4);
            int f0_3 = Measurement.measureBaseZ(state,7);
            int f0_4 = Measurement.measureBaseZ(state,10);

            boolean b1 = (f0_1 == 0 && f0_2 == 0 && f0_3 == 0 && f0_4 == 0);
            boolean b2 = (f0_1 == 0 && f0_2 == 0 && f0_3 == 1 && f0_4 == 1);
            boolean b3 = (f0_1 == 1 && f0_2 == 1 && f0_3 == 0 && f0_4 == 0);
            boolean b4 = (f0_1 == 1 && f0_2 == 1 && f0_3 == 1 && f0_4 == 1);

            if(!(b1 || b2 || b3 || b4)){
                sum += 1;
            }
        }

        return 1.0*sum/total;

    }
}
