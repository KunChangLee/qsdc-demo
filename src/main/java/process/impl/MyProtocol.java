package process.impl;

import receiver.Receiver;
import receiver.impl.Bob;
import sender.Sender;
import sender.impl.Alice;
import sender.impl.Charlie;
import util.Payload;

import java.util.*;

/**
 * Created by Zhao Zhe on 2017/9/16.
 */
public class MyProtocol extends AbastractProtocol {
    private Map<String,List> payload = new HashMap<String, List>();
    private List<Integer> has = new ArrayList<Integer>(1);

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public void setExtra(int extra) {
        this.extra = extra;
    }

    private int extra;
    private int message_lenth = 0;
    @Override
    public void authentication() {
        List<Integer> idc = generateIDC();
        payload.put(Payload.IDC,idc);
        List<Integer> idb = generateIDB();
        payload.put(Payload.IDB,idb);
        Charlie charlie = new Charlie();
        Receiver bob = new Bob();
        Alice alice = new Alice();
        charlie.addReceiver(alice);
        charlie.addReceiver(bob);

        alice.setMessage(message);
        charlie.send(alice,payload);
        alice.send(bob,payload);

        charlie.notifys();


    }

    @Override
    public void process() {
        String nMessage = formateMessage(message);
        this.message = nMessage;
        this.message_lenth = nMessage.length()/2;
        authentication();


    }

    @Override
    public void check() {
    }

    private List<Integer> generateIDC(){
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < message_lenth+extra; i++) {
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
        for (int i = 0; i < message_lenth; i++) {
            int temp = random.nextInt(2);
            if(temp == 0)
                list.add(0);
            else
                list.add(1);

        }

        return list;
    }
    private String formateMessage(String message){
        int len = message.length();
        if(len % 2 == 1){
            has.add(1);
            message = "0"+message;
        }else {
            has.add(0);
        }
        payload.put(Payload.HAS_EXTRA,has);

        return message;
    }
}
