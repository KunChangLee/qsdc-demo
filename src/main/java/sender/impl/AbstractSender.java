package sender.impl;

import receiver.Receiver;
import sender.Sender;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public abstract class AbstractSender implements Sender{



    protected void prepareState(Map<String,List> payload){};
    protected void doSend(Receiver receiver){};

    public void send(Receiver receiver, Map<String,List> payload) {
        prepareState(payload);
        doSend(receiver);
    }
}
