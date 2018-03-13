package chart;

import attacker.Attack;
import attacker.AttackStrategy;
import process.impl.MyProtocol;
import quantum.QuantumState;
import quantum.impl.*;
import util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Zhao Zhe on 2017/12/15.
 */
public class Analysis {

    private static List<Integer> generateIDC(int num){
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < num; i++) {
            int temp = random.nextInt(2);
            if(temp == 0)
                list.add(0);
            else
                list.add(1);

        }


        return list;
    }
    private static Map<String,List> prepareState(int num, int cat, Map<String,List> payload){
        List<QuantumState> sequence = new ArrayList<QuantumState>();
        List<Integer> mc = new ArrayList<Integer>();
        List<Integer> idc = payload.get(Payload.IDC);

        if(cat == 1){
            Random r2 = new Random();
            for (int i = 0; i < num; i++) {
                int basis = idc.get(i);
                int state = r2.nextInt(2);
                if(basis == 0){
                    if(state == 0){
                        sequence.add(new ComputaionState(0));
                        mc.add(0);
                    }else {
                        sequence.add(new ComputaionState(1));
                        mc.add(1);
                    }
                }else {
                    if(state == 0){
                        sequence.add(new HardamadState(0));
                        mc.add(0);
                    }else {
                        sequence.add(new HardamadState(1));
                        mc.add(1);
                    }
                }
            }

            payload.put(Payload.SEQUENCE,sequence);
            payload.put(Payload.MC,mc);

        }else if(cat == 2){
            for (int i = 0; i < num; i++) {
                sequence.add(new GHZState());

            }
            payload.put(Payload.SEQUENCE,sequence);
        }

        return payload;
    }

    private static double calcError(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<Integer> idc = payload.get(Payload.IDC);
        List<Integer> mc2 = new ArrayList<Integer>();
        List<Integer> mc = payload.get(Payload.MC);
        int error = 0;


        for (int i = 0; i < idc.size(); i++) {
            QuantumState state = null;
            if(idc.get(i) == 0){
                state = sequence.get(i);

                int result;

                result = Measurement.measureBaseZ(state,1);

                mc2.add(result);

            }else {
                state = sequence.get(i);

                int result;

                result = Measurement.measureBaseX(state,1);

                mc2.add(result);

            }
        }

        for (int i = 0; i < mc2.size(); i++) {
            int mc_res = mc.get(i);
            int mc2_res = mc2.get(i);
            if(mc2_res != mc_res)
                error += 1;
        }
        double errorRate = error*1.0/mc.size();

        return errorRate;
    }
    public static Map<String,List> prepareGHZ(double alpha, double gama, int num){

        Map<String,List> payload = new HashMap<String, List>();
        alpha = Math.sqrt(alpha);
        gama = Math.sqrt(gama);
        List<QuantumState> sequence = new ArrayList<QuantumState>();
        QuantumState eave = new EaveState();
        QuantumState state0 = new ComputaionState(0);
        QuantumState state1 = new ComputaionState(1);
        for (int i = 0; i < num; i++) {

            double[][] eaveState = Operation.transposition(eave.getState());



                double[][] state00 = Operation.transposition(state0.getState());
                double[][] state11 = Operation.transposition(state1.getState());


                double[][] temp = Operation.operatorTensor(state00,eaveState);
                double[][] temp1 = Operation.operatorTensor(state11,eaveState);

                    double[][] temp0_1 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
                    double[][] temp0_2 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
                    double[][] temp0_3 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp);
                    double[][] temp1_1 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
                    double[][] temp1_2 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
                    double[][] temp1_3 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);

                    double[][] part1 = Operation.operatorTensor(Operation.operatorTensor(temp0_1,temp0_2),temp0_3);
                    double[][] part2 = Operation.operatorTensor(Operation.operatorTensor(temp1_1,temp1_2),temp1_3);

                    part1 = Operation.add(part1,part2);
                    part1 = Operation.multiple(Constant.SQRT2,part1);

                    QuantumState target = new GHZState();
                    target.setParticles(9);
                    target.setState(Operation.vecToArray(part1));
                    sequence.add(target);



        }
        payload.put(Payload.SEQUENCE,sequence);
        return payload;

    }
    public static Map<String,List> prepareCluster(double alpha, double gama, int num){

        Map<String,List> payload = new HashMap<String, List>();
        List<QuantumState> sequence = new ArrayList<QuantumState>();
        QuantumState eave = new EaveState();
        QuantumState state0 = new ComputaionState(0);
        QuantumState state1 = new ComputaionState(1);
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
    public static Map<String,List> prepareW(double alpha, double gama, int num){

        Map<String,List> payload = new HashMap<String, List>();
        List<QuantumState> sequence = new ArrayList<QuantumState>();
        QuantumState eave = new EaveState();
        QuantumState state0 = new ComputaionState(0);
        QuantumState state1 = new ComputaionState(1);
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
                    double[][] temp1_1 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
                    double[][] temp1_2 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);
                    double[][] temp1_3 = Operation.innerProduct(Operators.getOperator_E2(alpha,gama),temp1);

                    double[][] part1 = Operation.operatorTensor(Operation.operatorTensor(temp1_1,temp0_1),temp0_2);
                    double[][] part2 = Operation.operatorTensor(Operation.operatorTensor(temp0_3,temp1_2),temp0_4);
                    double[][] part3 = Operation.operatorTensor(Operation.operatorTensor(temp0_5,temp0_6),temp1_3);

                    part1 = Operation.add(part1,part2);
                    part1 = Operation.add(part1,part3);
                    part1 = Operation.multiple(Constant.SQRT3,part1);

                    QuantumState target = new GHZState();
                    target.setParticles(9);
                    target.setState(Operation.vecToArray(part1));
                    sequence.add(target);



        }
        payload.put(Payload.SEQUENCE,sequence);
        return payload;

    }
    public static void analysisGHZ(int num){
        List<Double> alpha = new ArrayList<Double>();
        List<Double> gama = new ArrayList<Double>();
        List<Double> error = new ArrayList<Double>();

        for (double i = 0; i <= 1.0; i+=0.2) {
            for (double j = 0; j <= 1.0; j+=0.2) {


                Map<String,List> payload;

                System.out.println("alpha:" + i + ", gama:" + j);
                ExecutorService executor = Executors.newFixedThreadPool(10);
                List<Tasks> tasks = new ArrayList<Tasks>();
                double sum = 0;
                for (int k = 0; k < 100; k++) {
                    //payload = prepareGHZ(i,j,num);
                    //sum += calcGHZ(payload);
                    tasks.add(new Tasks(i,j));

                }
                try {
                    List<Future<Double>> ans = executor.invokeAll(tasks);
                    executor.shutdown();

                    for (Future<Double> f : ans){
                        double tmp = f.get();
                        sum += tmp;
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
                alpha.add(i);
                gama.add(j);
                error.add(sum/100);
                //System.out.println("error:" + sum/100);

            }
        }

        Map<String,List<Double>> map = new HashMap<String, List<Double>>();
        map.put("error",error);
        map.put("fac1",alpha);
        map.put("fac2",gama);

        compare(map,2);
        //try {
        //    //write(map);
        //    //Scatter3D.draw(map);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

    }
    public static void analysisCluster(int num){
        List<Double> alpha = new ArrayList<Double>();
        List<Double> gama = new ArrayList<Double>();
        List<Double> error = new ArrayList<Double>();

        for (double i = 0; i <= 1.0; i+=0.2) {
            for (double j = 0; j <= 1.0; j+=0.2) {


                Map<String,List> payload;

                System.out.println("alpha:" + i + ", gama:" + j);
                double sum = 0;
                ExecutorService executor = Executors.newFixedThreadPool(10);
                List<Tasks> tasks = new ArrayList<Tasks>();
                long start = System.currentTimeMillis();

                for (int k = 0; k < 100; k++) {
                    //long start = System.currentTimeMillis();

                    //payload = prepareCluster(i,j,num);
                    //System.out.println("num:" + k);
                    ////
                    //sum += calcCluster(payload);
                    //long end = System.currentTimeMillis();
                    //System.out.println("time:" + (end-start));
                    tasks.add(new Tasks(i,j));

                }

                try {
                    List<Future<Double>> ans = executor.invokeAll(tasks);
                    executor.shutdown();

                    for (Future<Double> f : ans){
                        double tmp = f.get();
                        sum += tmp;
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();
                System.out.println("time:" + (end-start));
                alpha.add(i);
                gama.add(j);
                error.add(sum/100);
                System.out.println("ideal:" + calcIdeal2(i,j,4));

                System.out.println("error:" + sum/100);

            }
        }

        Map<String,List<Double>> map = new HashMap<String, List<Double>>();
        map.put("error",error);
        map.put("fac1",alpha);
        map.put("fac2",gama);

        compare(map,4);

    }
    public static void analysisW(int num){
        List<Double> alpha = new ArrayList<Double>();
        List<Double> gama = new ArrayList<Double>();
        List<Double> error = new ArrayList<Double>();

        for (double i = 0; i <= 1.0; i+=0.2) {
            for (double j = 0; j <= 1.0; j+=0.2) {


                Map<String,List> payload;

                System.out.println("alpha:" + i + ", gama:" + j);
                double sum = 0;
                ExecutorService executor = Executors.newFixedThreadPool(10);
                long start = System.currentTimeMillis();

                List<Tasks> tasks = new ArrayList<Tasks>();
                for (int k = 0; k < 100; k++) {
                    //long start = System.currentTimeMillis();
                    //payload = prepareW(i,j,num);
                    //System.out.println("num:" + k);
                    //sum += calcW(payload);
                    //long end = System.currentTimeMillis();
                    //System.out.println("time:" + (end-start));
                    tasks.add(new Tasks(i,j));


                }

                try {
                    List<Future<Double>> ans = executor.invokeAll(tasks);
                    executor.shutdown();

                    for (Future<Double> f : ans){
                        double tmp = f.get();
                        sum += tmp;
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();
                System.out.println("time:" + (end-start));

                alpha.add(i);
                gama.add(j);
                error.add(sum/100);
                System.out.println("ideal:" + calcIdeal2(i,j,3));
                System.out.println("error:" + sum/100);

            }
        }

        Map<String,List<Double>> map = new HashMap<String, List<Double>>();
        map.put("error",error);
        map.put("fac1",alpha);
        map.put("fac2",gama);

        compare(map,3);

    }

    private static double calcGHZ(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        int total = sequence.size();
        int sum = 0;
        for (int i = 0; i < total; i++) {
            QuantumState state = sequence.get(i);
            int f0_1 = Measurement.measureBaseZ(state,1);
            int f0_2 = Measurement.measureBaseZ(state,4);
            int f0_3 = Measurement.measureBaseZ(state,7);

            if(!(f0_1 == f0_2 && f0_2 == f0_3)){
                sum += 1;
            }
        }

        return 1.0*sum/total;

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

            boolean b1 = (f0_1 == 1 && f0_2 == 0 && f0_3 == 0);
            boolean b2 = (f0_1 == 0 && f0_2 == 1 && f0_3 == 0);
            boolean b3 = (f0_1 == 0 && f0_2 == 0 && f0_3 == 1);

            if(!(b1 || b2 || b3)){
                sum += 1;
            }
        }

        return 1.0*sum/total;

    }
    private static double calcCluster(Map<String,List> payload){
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

    public static void analysisPhoton(int num){

        List<Double> alpha = new ArrayList<Double>();
        List<Double> gama = new ArrayList<Double>();
        List<Double> error = new ArrayList<Double>();

        for (double i = 0; i <= 1.0; i+=0.2) {
            for (double j = 0; j <= 1.0; j+=0.2) {

                List<Integer> idc = generateIDC(num);

                Map<String,List> payload = new HashMap<String, List>();

                System.out.println("alpha:" + i + ", gama:" + j);
                //ExecutorService executor = Executors.newFixedThreadPool(10);
                //List<Tasks> tasks = new ArrayList<Tasks>();
                double sum = 0;
                for (int k = 0; k < 100; k++) {
                    payload.put(Payload.IDC, idc);
                    payload = prepareState(num,1,payload);
                    Attack.entangleMeasureAttack(payload,i,j);
                    sum += calcError(payload);
                }
                alpha.add(i);
                gama.add(j);
                error.add(sum/100);
                System.out.println("error:" + sum/100);

            }
        }

        Map<String,List<Double>> map = new HashMap<String, List<Double>>();
        map.put("error",error);
        map.put("fac1",alpha);
        map.put("fac2",gama);

        compare(map,1);

        //try {
        //    write(map);
        //    Scatter3D.draw(map);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}


    }

    public static void analysisPhotonIdeal(){
        try {
           Surface3D.draw();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write(Map<String,List<Double>> payload) throws Exception{
        List<Double> error = payload.get("error");
        List<Double> alpha = payload.get("fac1");
        List<Double> gama = payload.get("fac2");

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("E:\\量子密码\\论文\\毕业论文\\Photon.txt")));
        for (int i = 0; i < error.size(); i++) {
            String str = alpha.get(i) + "," + gama.get(i) + "," + error.get(i) + "\n";
            bw.write(str);
            bw.flush();

        }
        bw.close();
    }

    private static void compare(Map<String,List<Double>> payload, int cat){
        List<Double> error = payload.get("error");

        List<Double> alpha = payload.get("fac1");
        List<Double> gama = payload.get("fac2");

        for (int i = 0; i < error.size(); i++) {
            double a = alpha.get(i);
            double g = gama.get(i);
            System.out.println("alpha:" + a + ", gama:" + g);
            System.out.println("Experiment result: " + error.get(i));
            System.out.println("Ideal result:" + calcIdeal2(a,g,cat));
            System.out.println("==========================");

        }


    }

    private static double calcIdeal(double x, double y, int cat){
        double result = 0;
        if(cat == 1){
            result = 0.5 - x*x/4 + y*y/4;
        }else if(cat == 2){
            double i = 1-x*x;
            double j = 1-y*y;
            result = 1 - (Math.pow(x,6)+Math.pow(y,6)+Math.pow(i,3)+Math.pow(j,3))/2;
        }else if(cat == 3){
            double i = Math.pow(x,4);
            double j = y*y;
            result = 1-(i-i*j*3+2*x*x*j);
        }else if(cat == 4){

            double a = Math.pow(x,4);
            double g = Math.pow(y,4);
            double b = Math.pow(1-x*x,2);
            double d = Math.pow(1-y*y,2);

            double part1 = (a*a+b*b+g*g+d*d)/4;
            double part2 = (a*b+a*g+a*d+b*g+b*d+g*d)/2;

            result = 1-part1-part2;



        }

        return result;

    }
    private static double calcIdeal2(double x, double y, int cat){
        double result = 0;
        if(cat == 0){
            double log2 = Math.log10(x)/Math.log10(2);
            double log2_1 = Math.log10(1-x)/Math.log10(2);
            result = 1-x*log2-(1-x)*log2_1;
        }
        else if(cat == 1){
            result = 0.5 - x/4 + y/4;
        }else if(cat == 2){
            result = 1 - (Math.pow(x,3)+Math.pow(y,3)+Math.pow(1-x,3)+Math.pow(1-y,3))/2;

        }else if(cat == 3){
            result = 1-(x*x-3*x*x*y+2*x*y);

        }else if(cat == 4){

            double part1 = Math.pow(x,4)+Math.pow(y,4);
            double part2 = 2*(Math.pow(x,3)+Math.pow(y,3));
            double part3 = 3*(Math.pow(x,2)+Math.pow(y,2));
            double part4 = 2*(x+y);
            double part5 = 2*(x*x*y*y-x*y*y-x*x*y+x*y);
            result = 0-(part1-part2+part3-part4+part5);

        }

        return result;

    }
    public static void analysize(){
        List<Double> data = new ArrayList();
        List<Double> result1 = new ArrayList<Double>();
        List<Double> result2 = new ArrayList<Double>();

        for (double i = 0.0; i <= 1.0; i+=0.01) {
            data.add(i);
            double y = 1-(3*Math.pow(i,3)-4*i*i+2*i);
            result1.add(y);
            result2.add(y);

        }

        Map<String,List<Double>> map = new HashMap<String, List<Double>>();
        map.put("data",data);
        map.put("result1",result1);
        map.put("result2",result2);
        TimeSeriesChart chart = new TimeSeriesChart(map);

    }

}
