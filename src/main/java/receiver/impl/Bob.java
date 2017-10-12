package receiver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quantum.QuantumState;
import receiver.Receiver;
import util.Constant;
import util.Measurement;
import util.Payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhao Zhe on 2017/9/22.
 */
public class Bob implements Receiver {
    private static Logger logger = LoggerFactory.getLogger(Bob.class);
    private Map<String,List> payload = null;
    private double threshold = 0;
    private double errorRate = 0;
    private int[][][] decode1 = {{{0,1,3,2},{2,3,1,0}},{{1,0,2,3},{3,2,0,1}}};
    private int[][][] decode2 = {{{1,0,2,3},{3,2,0,1}},{{0,1,3,2},{2,3,1,0}}};


    public String getSecret() {
        return message;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getErrorRate() {
        return errorRate;
    }

    private String message = "";


    public void notified() {

        String secret = decode();
        this.message = secret;
        logger.info("Bob's secret message is {}",secret);
    }

    public void receive(Map<String, List> payload) {
        logger.info("Bob receive Alice's sequence!");
        this.payload = payload;
        authentication(payload);
    }

    private void authentication(Map<String, List> payload){
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<Integer> idb = payload.get(Payload.IDB);
        List<Integer> mc2 = new ArrayList<Integer>();
        List<Integer> mb = payload.get(Payload.MB);
        List<QuantumState> temp = new ArrayList<QuantumState>();
        int error = 0;

        logger.info("Bob is authenticating Alice's identity...");

        for (int i = 0; i < idb.size(); i++) {
            QuantumState state = null;
            if(idb.get(i) == 0){
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
        logger.info("Bob compare the classical string of measurement....");

        for (int i = 0; i < mc2.size(); i++) {
            int mc_res = mb.get(i);
            int mc2_res = mc2.get(i);
            if(mc2_res != mc_res)
                error += 1;
        }
        logger.info("The error rate is {}%",String.format("%.2f",error*100.0/mb.size()));
        this.errorRate = error*1.0/mb.size();
        if(errorRate > threshold){
            logger.info("The error rate is over threshold, the communication is abort!");
            return;
        }
        payload.put(Payload.SEQUENCE,temp);
    }
    private String decode(){
        logger.info("Bob is measuring particle 3 and 4 ...");
        List<QuantumState> sequence = payload.get(Payload.SEQUENCE);
        List<String> operator = payload.get(Payload.CHARLIE_OPERATION_POS);
        List<Integer> alices = payload.get(Payload.ALICE_RESULT);
        List<Integer> charlies = payload.get(Payload.CHARLIE_RESULT);

        StringBuffer secret = new StringBuffer();

        for (int i = 0; i < sequence.size(); i++) {
            QuantumState state = sequence.get(i);
            String ope = operator.get(i);
            int charlie = charlies.get(i);
            int alice = alices.get(i);
            int measure = Measurement.measureBaseBell(state);
            int result;
            if(ope.equals(Constant.OPERATION_I)){
                result = decode1[alice][charlie][measure-1];
            }else {
                result = decode2[alice][charlie][measure-1];
            }

            switch (result){
                case 0:
                    secret.append("00");
                    break;
                case 1:
                    secret.append("01");
                    break;
                case 2:
                    secret.append("10");
                    break;
                case 3:
                    secret.append("11");
                    break;
            }

        }
        List<Integer> has = payload.get(Payload.HAS_EXTRA);
        if(has.get(0) == 1)
            secret.delete(0,1);
        return secret.toString();

    }
}
