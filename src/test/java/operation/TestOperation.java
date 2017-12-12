package operation;

import org.junit.Test;
import quantum.QuantumState;
import quantum.impl.ClusterState;
import quantum.impl.ComputaionState;
import quantum.impl.LogicClusterState;
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

        for (int i = 0; i < 10; i++) {
            QuantumState state = new ClusterState();
            Operation.noise(state,0.3,1);
            Operation.noise(state,0.3,3);
            Operation.noise(state,0.3,4);
            System.out.print(Measurement.measureBaseZ(state,1)+",");
            System.out.print(Measurement.measureBaseZ(state,2)+",");
            System.out.print(Measurement.measureBaseBell(state));
            System.out.println();

        }
    }
    @Test
    public void testLogicMeasurement(){

        for (int i = 0; i < 10; i++) {
            QuantumState state = new LogicClusterState();
            Operation.noise(state,0.3,1);
            Operation.noise(state,0.3,2);
            Operation.noise(state,0.3,5);
            Operation.noise(state,0.3,6);
            Operation.noise(state,0.3,7);
            Operation.noise(state,0.3,8);
            System.out.print(Measurement.logicMeasureBaseZ(state,1)+",");
            System.out.print(Measurement.logicMeasureBaseZ(state,2)+",");
            System.out.print(Measurement.logicMeasureBaseBell(state));
            System.out.println();

        }
    }
    @Test
    public void testLogicMeasurement2(){

        for (int i = 0; i < 10; i++) {
            QuantumState state = new LogicClusterState();
            Operation.noise(state,0.3,1);
            Operation.noise(state,0.3,2);
            Operation.noise(state,0.3,5);
            Operation.noise(state,0.3,6);
            Operation.noise(state,0.3,7);
            Operation.noise(state,0.3,8);
            System.out.print(Measurement.logicMeasureBaseZ(state,1)+",");
            System.out.print(Measurement.logicMeasureBaseZ(state,2)+",");
            System.out.print(Measurement.logicMeasureBaseBell(state));
            System.out.println();

        }
    }

    @Test
    public void testAdd(){
        double[][] result = Operation.add(Operators.Operator_I,Operators.Operator_X);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j]+",");
            }
            System.out.println();
        }
    }
}
