package sender.impl;

import attacker.Attack;
import attacker.Attacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quantum.QuantumState;
import quantum.impl.*;
import receiver.Receiver;
import sender.Sender;
import util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Zhao Zhe on 2017/9/16.
 */
public class Alice extends AbstractSender implements Receiver{
    private static Logger logger = LoggerFactory.getLogger(Alice.class);
    List<Integer> messageList = new ArrayList<Integer>();
    private List<Sender> senders = new ArrayList<Sender>();
    private List<Attacker> attackers = new ArrayList<Attacker>();
    private double errorRate = 0;
    private double errorRate2 = 0;
    private double threshold = 0;
    private double cos;
    private boolean isIdeal = true;

    public void setIdeal(boolean ideal) {
        isIdeal = ideal;
    }

    public void setCos(double cos) {
        this.cos = cos;
    }

    private String message;
    private Map<String,List> payload = null;

    private int[][] check1 = new int[][]{{1,4},{2,3}};
    private int[][] check2 = new int[][]{{2,3},{1,4}};

    public void setMessage(String message) {
        this.message = message;
    }
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public double getErrorRate2() {
        return errorRate2;
    }

    @Override
    protected void prepareState(Map<String, List> payload) {
        messageList(message);
        logger.info("Alice is prepare sequence for message sending...");
        if(isIdeal){
            prepareNormState(payload);
        }else {
            prepareLogicState(payload);
        }

        logger.info("Alice complete encode!");

    }
    private void prepareNormState(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<QuantumState> newSequence = new ArrayList<QuantumState>();
        List<Integer> idb = payload.get(Payload.IDB);
        List<Integer> mb = new ArrayList<Integer>();
        Random random = new Random();

        for (int i = 0; i < messageList.size(); i++) {

            int operator = messageList.get(i);
            QuantumState state = sequence.get(i);
            switch (operator){
                case 0:
                    break;
                case 1:
                    Operation.performOperator(state,4,Operators.Operator_X);
                    break;
                case 2:
                    Operation.performOperator(state,4,Operators.Operator_iY);
                    break;
                case 3:
                    Operation.performOperator(state,4,Operators.Operator_Z);
                    break;

            }



            int id = idb.get(i);
            int pos = random.nextInt(2);
            QuantumState decoy;
            if(id == 0){
                if(pos == 0){
                    decoy = new ComputaionState(0);
                    mb.add(0);
                }
                else{
                    decoy = new ComputaionState(1);
                    mb.add(1);
                }
                newSequence.add(state);
                newSequence.add(decoy);
            }else {
                if (pos == 0){
                    decoy = new HardamadState(0);
                    mb.add(0);
                }
                else{
                    decoy = new HardamadState(1);
                    mb.add(1);
                }
                newSequence.add(decoy);
                newSequence.add(state);
            }

        }
        payload.put(Payload.SEQUENCE,newSequence);
        payload.put(Payload.MB,mb);
    }
    private void prepareLogicState(Map<String,List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<QuantumState> newSequence = new ArrayList<QuantumState>();
        List<Integer> idb = payload.get(Payload.IDB);
        List<Integer> mb = new ArrayList<Integer>();
        Random random = new Random();

        for (int i = 0; i < messageList.size(); i++) {

            int operator = messageList.get(i);
            QuantumState state = sequence.get(i);
            switch (operator){
                case 0:
                    break;
                case 1:
                    Operation.performLogicOperator(state,4,Operators.Logic_X);
                    break;
                case 2:
                    Operation.performLogicOperator(state,4,Operators.Logic_Y);
                    break;
                case 3:
                    Operation.performLogicOperator(state,4,Operators.Logic_Z);
                    break;

            }



            int id = idb.get(i);
            int pos = random.nextInt(2);
            QuantumState decoy;
            if(id == 0){
                if(pos == 0){
                    decoy = new BellState(1);
                    mb.add(0);
                }
                else{
                    decoy = new BellState(4);
                    mb.add(1);
                }
                newSequence.add(state);
                newSequence.add(decoy);
            }else {
                if (pos == 0){
                    decoy = new LogicHardamadState(0);
                    mb.add(0);
                }
                else{
                    decoy = new LogicHardamadState(1);
                    mb.add(1);
                }
                newSequence.add(decoy);
                newSequence.add(state);
            }

        }
        payload.put(Payload.SEQUENCE,newSequence);
        payload.put(Payload.MB,mb);
    }

    @Override
    protected void doSend(Receiver receiver) {
        logger.info("Alice send the sequence to Bob...");
        for (Attacker attacker : attackers){
            attacker.attack(payload);
        }
        //Attack.noises(payload,cos);
        receiver.receive(payload);
    }




    public void receive(Map<String, List> payload) {
        this.payload = payload;
        logger.info("Alice receive Charlie's sequence!");
        if(!authentication(payload))
            return;
        checkParticles(payload);
    }

    public void notified() {
        logger.info("Alice is measuring particle 1...");
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<Integer> result = new ArrayList<Integer>();
        for(QuantumState state : sequence){
            int measure;
            if(isIdeal){
                measure = Measurement.measureBaseZ(state,1);
            }else {
                measure = Measurement.logicMeasureBaseZ(state, 1);
            }
            result.add(measure);
        }
        payload.put(Payload.ALICE_RESULT,result);
    }

    private boolean authentication(Map<String, List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<Integer> idc = payload.get(Payload.IDC);
        List<Integer> mc2 = new ArrayList<Integer>();
        List<Integer> mc = payload.get(Payload.MC);
        List<QuantumState> temp = new ArrayList<QuantumState>();
        int error = 0;

        logger.info("Alice is authenticating Charlie's identity...");

        for (int i = 0; i < idc.size(); i++) {
            QuantumState state = null;
            if(idc.get(i) == 0){
                state = sequence.get(2*i+1);
                temp.add(sequence.get(2*i));
                int result;
                if(isIdeal){
                    result = Measurement.measureBaseZ(state,1);
                }else {
                    result = Measurement.logicMeasureBaseZ(state,1);
                }
                mc2.add(result);

            }else {
                state = sequence.get(2*i);
                temp.add(sequence.get(2*i+1));
                int result;
                if(isIdeal){
                    result = Measurement.measureBaseX(state,1);
                }else {
                    result = Measurement.logicMeasureBaseX(state,1);
                }
                mc2.add(result);

            }
        }
        logger.info("Alice compare the classical string of measurement....");

        for (int i = 0; i < mc2.size(); i++) {
            int mc_res = mc.get(i);
            int mc2_res = mc2.get(i);
            if(mc2_res != mc_res)
                error += 1;
        }
        this.errorRate = error*1.0/mc.size();
        logger.info("The error rate is {}%",String.format("%.2f",error*100.0/mc.size()));
        if(errorRate > threshold){
            logger.info("The error rate is over threshold, the communication is abort!");
            return false;
        }

        payload.put(Payload.SEQUENCE,temp);
        return true;


    }
    private void messageList(String message){
        for (int i = 0; i < message.length()-1; i+=2) {
            String temp = message.substring(i,i+2);
            if(temp.equals("00"))
                this.messageList.add(0);
            else if(temp.equals("01"))
                this.messageList.add(1);
            else if(temp.equals("10"))
                this.messageList.add(2);
            else
                this.messageList.add(3);

        }
    }

    private void checkParticles(Map<String,List> payload){
        logger.info("Alice start checking the cluster state...");
        int extra = (Integer) payload.get(Payload.EXTRA).get(0);
        int size = payload.get(Payload.IDC).size();
        List<Integer> checks = new ArrayList<Integer>();
        Random random = new Random();
        List<QuantumState> temp = new ArrayList<QuantumState>();
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<String> ops = payload.get(Payload.CHARLIE_OPERATION_POS);
        List<String> opsTemp = new ArrayList<String>();
        int error = 0;
        int count = extra;

        while(count > 0){
            int ran = random.nextInt(size);
            if(!checks.contains(ran)){
                checks.add(ran);
                count -= 1;
            }
        }
        for (Sender s : senders){
            s.check(checks);
        }

        List<Integer> senderResults = payload.get(Payload.CHARLIE_CHECK_RESULT);
        List<Integer> aliceResults = new ArrayList<Integer>();
        List<Integer> bellResults = new ArrayList<Integer>();
        List<String> charlieOp = new ArrayList<String>();

        for (int i = 0; i < sequence.size(); i++) {
            QuantumState state = sequence.get(i);
            if(checks.contains(i)){
                int zResult;
                int bellResult;
                if(isIdeal){
                    zResult = Measurement.measureBaseZ(state,1);
                    bellResult = Measurement.measureBaseBell(state);
                }else {
                    zResult = Measurement.logicMeasureBaseZ(state,1);
                    bellResult = Measurement.logicMeasureBaseBell(state);
                }


                aliceResults.add(zResult);
                bellResults.add(bellResult);
                charlieOp.add(ops.get(i));
            }else {
                temp.add(state);
                opsTemp.add(ops.get(i));
            }
        }

        for (int i = 0; i < senderResults.size(); i++) {
            String op = charlieOp.get(i);
            int real = 0;
            int should = 0;
            if(op.equals(Constant.OPERATION_I)){
                real = bellResults.get(i);
                should = check1[aliceResults.get(i)][senderResults.get(i)];
            }else {
                real = bellResults.get(i);
                should = check2[aliceResults.get(i)][senderResults.get(i)];
            }

            if(real != should){
                error += 1;
            }
        }
        double rate = error*1.0/extra;
        this.errorRate2 = rate;
        logger.info("Alice complete the state checking!");
        logger.info("The error rate is {}%",String.format("%.2f",error*100.0/extra));
        if(errorRate2 > threshold){
            logger.info("Charlie prepared fake entangled states!Abort the communication!");
            return;
        }


        payload.put(Payload.SEQUENCE,temp);
        payload.put(Payload.CHARLIE_OPERATION_POS,opsTemp);
    }

    public void addSender(Sender sender){
        this.senders.add(sender);
    }
    public void addAttacker(Attacker attacker){
        this.attackers.add(attacker);
    }

}
