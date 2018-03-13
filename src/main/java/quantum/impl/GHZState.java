package quantum.impl;

import quantum.QuantumState;
import util.Constant;

/**
 * Created by Zhao Zhe on 2017/12/14.
 */
public class GHZState implements QuantumState {
    private int particles = 3;
    private double[] state;

    public GHZState(){
        this.state = new double[]{Constant.SQRT2,0,0,0,0,0,0,Constant.SQRT2};
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
