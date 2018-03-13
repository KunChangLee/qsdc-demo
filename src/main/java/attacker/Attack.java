package attacker;

import quantum.QuantumState;
import quantum.impl.*;
import util.Measurement;
import util.Operation;
import util.Operators;
import util.Payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Zhao Zhe on 2017/10/9.
 */
public class Attack {

    public static void entangleMeasureAttack(Map<String,List> payload, double alpha, double gama){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);

        for (QuantumState state : sequence){
            if(!(state instanceof ClusterState)){
                QuantumState eaveState = new EaveState();
                //eaveState = new ComputaionState(0);
                Operation.entangleAttack(state,eaveState,alpha,gama);

            }
        }
    }
    public static void modifyAttack(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);

        int random = new Random().nextInt(2);
        for (QuantumState state : sequence){
            if(random == 1){
                Operation.performOperator(state,1,Operators.Operator_X);
            }
        }
    }
    public static void resendAttack(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        int len = sequence.size();
        List<QuantumState> newSequence = new ArrayList<QuantumState>();

        for (int i = 0; i < len; i++) {
            int base = new Random().nextInt(2);
            int state = new Random().nextInt(2);
            QuantumState st;
            if (base == 0){
                if (state == 0){
                    st = new ComputaionState(0);
                }else {
                    st = new ComputaionState(1);
                }
            }else {
                if (state == 0){
                    st = new HardamadState(0);
                }else {
                    st = new HardamadState(1);
                }
            }
            newSequence.add(st);

        }
        payload.put(Payload.SEQUENCE,newSequence);

    }
    public static void measureResendAttack(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<QuantumState> newSequence = new ArrayList<QuantumState>();
        for (int i = 0; i < sequence.size(); i++) {
            QuantumState state = sequence.get(i);
            int result = Measurement.measureBaseZ(state,1);
            if(result == 0){
                newSequence.add(new ComputaionState(0));
            }else {
                newSequence.add(new ComputaionState(1));
            }
        }

        payload.put(Payload.SEQUENCE,newSequence);

    }
    public static void noises(Map<String,List> payload, double fac){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);

        for (QuantumState state : sequence){
            if(state instanceof ClusterState){
                Operation.noise(state,fac,1);
                Operation.noise(state,fac,3);
                Operation.noise(state,fac,4);

            }else if(state instanceof LogicClusterState){
                Operation.noise(state,fac,1);
                Operation.noise(state,fac,2);
                Operation.noise(state,fac,5);
                Operation.noise(state,fac,6);
                Operation.noise(state,fac,7);
                Operation.noise(state,fac,8);
            }else if(state instanceof LogicHardamadState){
                Operation.noise(state,fac,1);
                Operation.noise(state,fac,2);
            }else if(state instanceof BellState){
                Operation.noise(state,fac,1);
                Operation.noise(state,fac,2);
            }
            else {
                Operation.noise(state,fac,1);

            }
        }
    }
}
