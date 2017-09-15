import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        QuantumState state = new ClusterState();

        List<QuantumState> list = new ArrayList<QuantumState>();
        for (int i = 0; i < 5; i++) {
            list.add(new ClusterState());
        }

        for (int i = 0; i < list.size(); i++) {

                System.out.print(Measurement.measureBaseBell(list.get(i)) + ",");
                System.out.print(Measurement.measureBaseZ(list.get(i),1) + ",");
                System.out.print(Measurement.measureBaseZ(list.get(i),2) + ",");

            System.out.println();
        }

        state = new ComputaionState(0);
        System.out.println(Measurement.measureBaseX(state,1));
    }


}
