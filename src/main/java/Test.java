import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import process.Protocol;
import process.impl.MyProtocol;
import quantum.QuantumState;
import quantum.impl.ClusterState;
import quantum.impl.ComputaionState;
import util.Measurement;
import util.Operation;
import util.Operators;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zhao Zhe on 2017/9/7.
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {

        MyProtocol protocol = new MyProtocol();
        protocol.setExtra(5);
        protocol.setMessage("你好啊！hello world!",true);
        protocol.process();

        System.out.println();
        protocol.setMessage("我爱北京,I love Beijing!",true);
        protocol.process();
    }


}
