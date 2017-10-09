package quantum.impl;

import quantum.QuantumState;

/**
 * Created by Zhao Zhe on 2017/9/12.
 */
public class ComputaionState implements QuantumState{

    private double[] state;
    private int particles = 1;

    public ComputaionState(int i){
        if(i == 0)
            this.state = new double[]{1,0};
        else
            this.state = new double[]{0,1};
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
