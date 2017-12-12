package quantum.impl;

import quantum.QuantumState;
import util.Operation;

/**
 * Created by Zhao Zhe on 2017/12/10.
 */
public class LogicClusterState implements QuantumState{
    private int particles = 8;
    private double[] state;

    public LogicClusterState(){
        QuantumState logicOne = new BellState(4);
        QuantumState logicZero = new BellState(1);
        double[][] one = Operation.transposition(logicOne.getState());
        double[][] zero = Operation.transposition(logicZero.getState());
        double[][] part1 = Operation.operatorTensor(Operation.operatorTensor(Operation.operatorTensor(zero,zero),zero),zero);
        double[][] part2 = Operation.operatorTensor(Operation.operatorTensor(Operation.operatorTensor(zero,zero),one),one);
        double[][] part3 = Operation.operatorTensor(Operation.operatorTensor(Operation.operatorTensor(one,one),zero),zero);
        double[][] part4 = Operation.operatorTensor(Operation.operatorTensor(Operation.operatorTensor(one,one),one),one);

        double[][] cluster = Operation.sub(Operation.add(Operation.add(part1,part2),part3),part4);
        this.state = Operation.vecToArray(Operation.multiple(0.5,cluster));

    }
    public double[] getState() {
        return this.state;
    }

    public void setState(double[] state) {
        this.state = state;

    }

    public int getParticles() {
        return this.particles;
    }

    public void setParticles(int num) {

        this.particles = num;
    }
}
