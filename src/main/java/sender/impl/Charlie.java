package sender.impl;

import attacker.Attack;
import attacker.Attacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quantum.QuantumState;
import quantum.impl.*;
import receiver.Receiver;
import util.*;

import java.util.*;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public class Charlie extends AbstractSender {
    private static Logger logger = LoggerFactory.getLogger(Charlie.class);


    private int message_lenth = 0;
    private Map<String,List> payload = new HashMap<String, List>();
    private List<Receiver> listener = new ArrayList<Receiver>();
    private List<Attacker> attackers = new ArrayList<Attacker>();
    private boolean isIdeal = true;
    private double cos;

    public void setCos(double cos) {
        this.cos = cos;
    }

    public void setIdeal(boolean ideal) {
        isIdeal = ideal;
    }



    @Override
    protected void prepareState(Map<String,List> payload) {
        if(isIdeal){
            prepareNormState(payload);
        }else {
            prepareLogicState(payload);
        }
    }
    private void prepareNormState(Map<String,List> payload){
        this.payload = payload;
        this.message_lenth = payload.get(Payload.IDC).size();
        logger.info("Charlie is preparing the cluster state....");
        Random random = new Random();
        Random random2 = new Random();
        Random random3 = new Random();


        List<QuantumState> list = new ArrayList<QuantumState>();
        List<String> operation = new ArrayList<String>();
        List<Integer> idc = payload.get(Payload.IDC);
        List<Integer> mc = new ArrayList<Integer>();


        for (int i = 0; i < message_lenth; i++) {
            int next = random.nextInt(2);
            QuantumState state = new ClusterState();
            if(next == 1){
                Operation.performOperator(state,1, Operators.Operator_X);
                Operation.performOperator(state,4, Operators.Operator_X);
            }

            int op = random2.nextInt(2);

            if(op == 0){
                Operation.performOperator(state,1,Operators.Operator_I);
                operation.add(Constant.OPERATION_I);
            }else {
                Operation.performOperator(state,1, Operators.Operator_X);
                operation.add(Constant.OPERATION_X);
            }

            int id = idc.get(i);
            int pos = random3.nextInt(2);
            QuantumState decoy;
            if(id == 0){
                if(pos == 0){
                    decoy = new ComputaionState(0);
                    mc.add(0);
                }
                else{
                    decoy = new ComputaionState(1);
                    mc.add(1);
                }
                list.add(state);
                list.add(decoy);
            }else {
                if (pos == 0){
                    decoy = new HardamadState(0);
                    mc.add(0);
                }
                else{
                    decoy = new HardamadState(1);
                    mc.add(1);
                }
                list.add(decoy);
                list.add(state);
            }

        }

        payload.put(Payload.SEQUENCE,list);
        payload.put(Payload.CHARLIE_OPERATION_POS,operation);
        payload.put(Payload.MC,mc);

        logger.info("Charlie complete the preparation!");



    }
    private void prepareLogicState(Map<String,List> payload){
        this.payload = payload;
        this.message_lenth = payload.get(Payload.IDC).size();
        logger.info("Charlie is preparing the cluster state....");
        Random random = new Random();
        Random random2 = new Random();
        Random random3 = new Random();


        List<QuantumState> list = new ArrayList<QuantumState>();
        List<String> operation = new ArrayList<String>();
        List<Integer> idc = payload.get(Payload.IDC);
        List<Integer> mc = new ArrayList<Integer>();


        for (int i = 0; i < message_lenth; i++) {
            int next = random.nextInt(2);
            QuantumState state = new LogicClusterState();
            if(next == 1){
                Operation.performLogicOperator(state,1, Operators.Logic_X);
                Operation.performLogicOperator(state,4, Operators.Logic_X);
            }

            int op = random2.nextInt(2);

            if(op == 0){
                Operation.performLogicOperator(state,1,Operators.Logic_I);
                operation.add(Constant.OPERATION_I);
            }else {
                Operation.performLogicOperator(state,1, Operators.Logic_X);
                operation.add(Constant.OPERATION_X);
            }

            int id = idc.get(i);
            int pos = random3.nextInt(2);
            QuantumState decoy;
            if(id == 0){
                if(pos == 0){
                    decoy = new BellState(1);
                    mc.add(0);
                }
                else{
                    decoy = new BellState(4);
                    mc.add(1);
                }
                list.add(state);
                list.add(decoy);
            }else {
                if (pos == 0){
                    decoy = new LogicHardamadState(0);
                    mc.add(0);
                }
                else{
                    decoy = new LogicHardamadState(1);
                    mc.add(1);
                }
                list.add(decoy);
                list.add(state);
            }

        }

        payload.put(Payload.SEQUENCE,list);
        payload.put(Payload.CHARLIE_OPERATION_POS,operation);
        payload.put(Payload.MC,mc);

        logger.info("Charlie complete the preparation!");



    }

    @Override
    protected void doSend(Receiver receiver) {
        logger.info("Charlie send the quantum sequence to Alice...");

        for (Attacker attacker : attackers){
            attacker.attack(payload);
        }
        Attack.noises(payload,cos);
        receiver.receive(payload);
    }

    public void addReceiver(Receiver receiver){
        this.listener.add(receiver);
    }
    public void addAttacker(Attacker attacker){
        this.attackers.add(attacker);
    }
    public void notifys(){
        logger.info("Charlie allows Bob to recover the secret message");
        logger.info("Charlie is measuring particle 2...");
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<Integer> result = new ArrayList<Integer>();
        for(QuantumState state : sequence){
            int measure;
            if(isIdeal){
                measure = Measurement.measureBaseZ(state,2);

            }else {
                measure = Measurement.logicMeasureBaseZ(state,2);
            }
            result.add(measure);
        }
        payload.put(Payload.CHARLIE_RESULT,result);

        for(Receiver receiver : listener){
            receiver.notified();
        }
    }
    @Override
    public void check(List<Integer> list){
        Collections.sort(list);
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<Integer> results = new ArrayList<Integer>();
        for (int i : list){
            QuantumState state = sequence.get(i);
            int result;
            if(isIdeal){
                result = Measurement.measureBaseZ(state,2);

            }else {
                result = Measurement.logicMeasureBaseZ(state,2);
            }
            results.add(result);
        }
        payload.put(Payload.CHARLIE_CHECK_RESULT,results);
    }


}
