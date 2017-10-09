package quantum.impl;

import quantum.QuantumState;
import util.Constant;

/**
 * Created by Zhao Zhe on 2017/9/12.
 */
public class HardamadState implements QuantumState {
    private double[] state;
    private int particles = 1;

    public HardamadState(int i){
        if(i == 0)
            this.state = new double[]{Constant.SQURT2,Constant.SQURT2};
        else
            this.state = new double[]{Constant.SQURT2,-Constant.SQURT2};
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
