package quantum.impl;

import quantum.QuantumState;

/**
 * Created by Zhao Zhe on 2017/10/9.
 */
public class EaveState implements QuantumState{
    // |00>
    private double[] state;
    private int particles = 2;

    public EaveState(){
        this.state = new double[]{1,0,0,0};
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
