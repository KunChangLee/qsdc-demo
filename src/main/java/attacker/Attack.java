package attacker;

import quantum.QuantumState;
import quantum.impl.ClusterState;
import quantum.impl.EaveState;
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
        //Operators.setAlphaAndGama(1,1);
        for (QuantumState state : sequence){
            if(!(state instanceof ClusterState)){
                QuantumState eaveState = new EaveState();
                Operation.entangleAttack(state,eaveState);

            }
        }
    }
}
