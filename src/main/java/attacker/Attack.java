package attacker;

import quantum.QuantumState;
import quantum.impl.*;
import util.Operation;
import util.Operators;
import util.Payload;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhao Zhe on 2017/10/9.
 */
public class Attack {

    public static void EntangleMeasureAttack(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);

        for (QuantumState state : sequence){
            if(!(state instanceof ClusterState)){
                QuantumState eaveState = new EaveState();
                eaveState = new ComputaionState(0);
                Operation.entangleAttack(state,eaveState,Math.sqrt(0.5),Math.sqrt(0.5));

            }
        }
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
