package protocol;

import org.junit.Test;
import process.impl.MyProtocol;

/**
 * Created by Zhao Zhe on 2017/10/9.
 */
public class TestProtocol {

    @Test
    public void testProtocol(){
        MyProtocol protocol = new MyProtocol();
        protocol.setExtra(5);
        protocol.setMessage("你好啊！hello world!",true);
        protocol.process();

        System.out.println();
        protocol.setMessage("我爱北京,I love Beijing!",true);
        protocol.process();
    }
}
