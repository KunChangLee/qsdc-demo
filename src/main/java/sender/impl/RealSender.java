package sender.impl;

import receiver.Receiver;

import java.util.Map;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public class RealSender extends AbstractSender {

    private String name;
    private Map<String,Object> payload;

    public String getName() {
        return name;
    }

    public RealSender(String name){
        this.name = name;
    }

    @Override
    protected void prepareState() {
        super.prepareState();
    }

    @Override
    protected void doSend(Receiver receiver) {
        receiver.receive(payload);
    }
}
