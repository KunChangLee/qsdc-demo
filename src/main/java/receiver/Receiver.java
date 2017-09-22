package receiver;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public interface Receiver {
    void receive(Map<String,List> payload);
    void notified();
}
