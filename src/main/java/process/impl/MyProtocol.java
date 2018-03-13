package process.impl;

import attacker.Attack;
import attacker.AttackStrategy;
import attacker.Attacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import receiver.impl.Bob;
import sender.impl.Alice;
import sender.impl.Charlie;
import util.MessageUtil;
import util.Payload;

import java.util.*;

/**
 * Created by Zhao Zhe on 2017/9/16.
 */
public class MyProtocol extends AbastractProtocol {
    private static Logger logger = LoggerFactory.getLogger(MyProtocol.class);

    private Map<String,List> payload = new HashMap<String, List>();
    private List<Integer> has = new ArrayList<Integer>(1);
    private List<Integer> encodeList = null;
    private boolean isEncode = false;
    private int extra;
    private double cos;
    private int message_length = 0;
    private String message;
    private String originalMessage;
    private String secret = "";
    private String binResult = "";
    private double threshold = 0;
    private boolean isIdeal = true;
    private String[] strategy;
    private double c2aError;
    private double c2aError2;
    private double a2bError;
    private boolean abort = false;
    private Alice alice = new Alice();
    private Charlie charlie = new Charlie();
    private Bob bob = new Bob();

    public List<Integer> getIdc() {
        return idc;
    }

    List<Integer> idc;

    public void setIdc(List<Integer> idc) {
        this.idc = idc;
    }

    public void setAttackFac(List<Double> attackFac) {

        this.payload.put(Payload.ATTACK_FAC,attackFac);
    }

    public void setCos(double cos) {
        this.cos = cos;
    }

    public String getSecret() {
        return secret;
    }
    public double[] getError(){
        double[] error = new double[2];
        error[0] = c2aError;
        error[1] = a2bError;
        return error;
    }

    public void setThreshold(double threshold) {

        this.threshold = threshold;
    }

    public void setIdeal(boolean ideal) {
        isIdeal = ideal;
    }

    public void setStrategy(String[] strategy) {
        this.strategy = strategy;
    }

    public void setMessage(String message, boolean flag) {
        if(flag){
            this.message = MessageUtil.encodeMessage(message);
            this.encodeList = MessageUtil.getEncodeList();
            this.isEncode = true;

        }else {
            this.message = message;
        }
        this.originalMessage = this.message;

    }



    public void setExtra(int extra) {
        this.extra = extra;
    }


    @Override
    public void authentication() {
        if(!payload.containsKey(Payload.IDC)){
            idc = generateIDC();
            payload.put(Payload.IDC,idc);
        }

        List<Integer> idb = generateIDB();
        payload.put(Payload.IDB,idb);
        charlie = new Charlie();
        bob = new Bob();
        alice = new Alice();
        charlie.setIdeal(isIdeal);
        alice.setIdeal(isIdeal);
        bob.setIdeal(isIdeal);
        charlie.setCos(cos);
        alice.setCos(cos);
        for (int i = 0; i < strategy.length; i++) {
            String str = strategy[i];

            if(str.equals(AttackStrategy.NONE))
                continue;
            Attacker eave = new Attacker(str);
            charlie.addAttacker(eave);
            alice.addAttacker(eave);
        }



        charlie.addReceiver(alice);
        charlie.addReceiver(bob);
        alice.addSender(charlie);


        alice.setMessage(message);
        alice.setThreshold(threshold);
        bob.setThreshold(threshold);
        charlie.send(alice,payload);
        this.c2aError = alice.getErrorRate();
        this.c2aError2 = alice.getErrorRate2();
        if(c2aError2 > threshold || c2aError > threshold){
            this.abort = true;
            return;
        }
        alice.send(bob,payload);
        this.a2bError = bob.getErrorRate();
        if(a2bError > threshold){
            this.abort = true;
            return;
        }


        charlie.notifys();
        this.secret = bob.getSecret();
        this.binResult = secret;


    }

    @Override
    public void process() {
        long startTime = System.currentTimeMillis();
        String nMessage = formatMessage(this.message);
        this.message = nMessage;
        this.message_length = nMessage.length()/2;
        authentication();
        if(abort)
            return;


        if(this.isEncode){
            this.secret = MessageUtil.decodeMessage(secret,encodeList);
        }
        double errorRate = errorRate(this.originalMessage,this.binResult)*100;
        logger.info("Bob's secret message is: {}",secret);
        logger.info("The error rate is {}%.",String.format("%.2f",errorRate));


        long dur = System.currentTimeMillis() - startTime;
        logger.info("The protocol complete in {} ms!",String.format("%.2f",dur*0.1));
    }

    @Override
    public void check() {
    }

    private List<Integer> generateIDC(){
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();
        if(extra < 0){
            logger.error("Extra particles must more than 0!");
            System.exit(-1);
        }else {
            List<Integer> ex = new ArrayList<Integer>();
            ex.add(extra);
            payload.put(Payload.EXTRA,ex);
        }
        for (int i = 0; i < message_length+extra; i++) {
            int temp = random.nextInt(2);
            if(temp == 0)
                list.add(0);
            else
                list.add(1);

        }

        return list;
    }
    private List<Integer> generateIDB(){
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < message_length; i++) {
            int temp = random.nextInt(2);
            if(temp == 0)
                list.add(0);
            else
                list.add(1);

        }

        return list;
    }
    private String formatMessage(String message){
        int len = message.length();
        has.clear();
        if(len % 2 == 1){
            has.add(1);
            message = "0"+message;
        }else {
            has.add(0);
        }
        payload.put(Payload.HAS_EXTRA,has);

        return message;
    }

    private double errorRate(String message,String secret){
        int error = 0;
        for (int i = 0; i < secret.length(); i++) {
            char ch1 = message.charAt(i);
            char ch2 = secret.charAt(i);
            if(ch1 != ch2)
                error += 1;
        }
        return error*0.1/secret.length();
    }
}
