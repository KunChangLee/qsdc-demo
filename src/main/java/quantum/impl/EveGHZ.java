package quantum.impl;

import quantum.QuantumState;
import util.Constant;

/**
 * Created by Zhao Zhe on 2017/12/19.
 */
public class EveGHZ implements QuantumState {
    private int particles = 512;
    private double[] state;
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

    public EveGHZ(double alpha, double gama){
        double beta = Math.sqrt((1-alpha*alpha));
        double delta = Math.sqrt((1-gama*gama));
        double f1 = alpha*alpha*alpha* Constant.SQRT2;
        double f2 = alpha*alpha*beta*Constant.SQRT2;
        double f3 = alpha*beta*beta*Constant.SQRT2;
        double f4 = beta*beta*beta*Constant.SQRT2;
        double f5 = gama*gama*gama*Constant.SQRT2;
        double f6 = gama*gama*delta*Constant.SQRT2;
        double f7 = gama*delta*delta*Constant.SQRT2;
        double f8 = delta*delta*delta*Constant.SQRT2;
        double[] st = new double[512];
        for (int i = 0; i < 512; i++) {
            if(i == 0){
                st[i] = f1;
            }
            if(i == 4 || i == 39 || i==319){
                st[i] = f2;
            }
            if(i == 44 || i == 324 || i==359){
                st[i] = f3;
            }
            if(i == 364){
                st[i] = f4;
            }
            if(i == 145){
                st[i] = f5;
            }
            if(i == 150 || i == 185 || i==465){
                st[i] = f6;
            }
            if(i == 190 || i == 470 || i==505){
                st[i] = f7;
            }
            if(i == 511){
                st[i] = f8;
            }

        }

        this.state = st;
    }
}
