package sender.impl;

import receiver.Receiver;
import sender.Sender;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public abstract class AbstractSender implements Sender{

    protected void prepareState(){};
    protected void doSend(Receiver receiver){};

    public void send(Receiver receiver) {
        prepareState();
        doSend(receiver);
    }
}
