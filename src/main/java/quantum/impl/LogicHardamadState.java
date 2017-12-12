package quantum.impl;

import quantum.QuantumState;
import util.Constant;
import util.Operation;

/**
 * Created by Zhao Zhe on 2017/12/10.
 */
public class LogicHardamadState implements QuantumState {

    private double[] state;
    private int particles = 2;


    public LogicHardamadState(int i){
        QuantumState logicOne = new BellState(4);
        QuantumState logicZero = new BellState(1);
        double[][] one = Operation.transposition(logicOne.getState());
        double[][] zero = Operation.transposition(logicZero.getState());
        double[][] st;
        if(i == 0){
            st = Operation.add(one,zero);
            st = Operation.multiple(Constant.SQURT2,st);
            this.state = Operation.vecToArray(st);
        }else {
            st = Operation.sub(zero,one);
            st = Operation.multiple(Constant.SQURT2,st);
            this.state = Operation.vecToArray(st);
        }
    }
    public double[] getState() {
        return this.state;
    }

    public void setState(double[] state) {

        this.state = state;
    }

    public int getParticles() {
        return particles;
    }

    public void setParticles(int num) {

        this.particles = num;
    }
}
