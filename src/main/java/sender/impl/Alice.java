package sender.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quantum.QuantumState;
import quantum.impl.ClusterState;
import quantum.impl.ComputaionState;
import quantum.impl.HardamadState;
import receiver.Receiver;
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

    private String message;
    private Map<String,List> payload = null;

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    protected void prepareState(Map<String, List> payload) {
        messageList(message);
        logger.info("Alice is prepare sequence for message sending...");
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
        logger.info("Alice complete encode!");

    }

    @Override
    protected void doSend(Receiver receiver) {
        logger.info("Alice send the sequence to Bob...");
        receiver.receive(payload);
    }




    public void receive(Map<String, List> payload) {
        this.payload = payload;
        logger.info("Alice receive Charlie's sequence!");
        authentication(payload);



    }

    public void notified() {
        logger.info("Alice is measuring particle 1...");
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<Integer> result = new ArrayList<Integer>();
        for(QuantumState state : sequence){
            int measure = Measurement.measureBaseZ(state,1);
            result.add(measure);
        }
        payload.put(Payload.ALICE_RESULT,result);
    }

    private void authentication(Map<String, List> payload){
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
                int result = Measurement.measureBaseZ(state,1);
                mc2.add(result);

            }else {
                state = sequence.get(2*i);
                temp.add(sequence.get(2*i+1));
                int result = Measurement.measureBaseX(state,1);
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
        logger.info("The error rate is {}",error*1.0/mc.size());
        payload.put(Payload.SEQUENCE,temp);


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
}
