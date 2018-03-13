package quantum.impl;

import quantum.QuantumState;
import util.Constant;

/**
 * Created by Zhao Zhe on 2017/12/10.
 */
public class BellState implements QuantumState {
    private int particles = 2;
    private double[] state;

    public BellState(int i){
        switch (i){
            case 1:
                this.state = new double[]{Constant.SQRT2,0,0,Constant.SQRT2};
                break;
            case 2:
                this.state = new double[]{0,Constant.SQRT2,Constant.SQRT2,0};
                break;
            case 3:
                this.state = new double[]{Constant.SQRT2,0,0,-Constant.SQRT2};
                break;
            case 4:
                this.state = new double[]{0,Constant.SQRT2,-Constant.SQRT2,0};
                break;
        }
    }

    public double[] getState() {
        return state;
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
