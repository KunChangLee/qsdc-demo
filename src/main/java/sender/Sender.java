package sender;

import receiver.Receiver;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public interface Sender {
    void send(Receiver receiver, Map<String,List> payload);
}
