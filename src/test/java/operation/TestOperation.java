package operation;

import org.junit.Test;
import quantum.QuantumState;
import quantum.impl.ClusterState;
import quantum.impl.ComputaionState;
import util.Measurement;
import util.Operation;
import util.Operators;

/**
 * Created by Zhao Zhe on 2017/9/22.
 */
public class TestOperation {

    @Test
    public void testOperation(){
        QuantumState state = new ClusterState();
        Operation.performOperator(state,4, Operators.Operator_X);
        for (int i = 0; i < 16; i++) {
            System.out.print(state.getState()[i]+",");
        }
    }
    @Test
    public void testMeasurement(){
        QuantumState state = new ComputaionState(0);
        for (int i = 0; i < 10; i++) {
            System.out.println(Measurement.measureBaseX(state,1));

        }
    }
}
