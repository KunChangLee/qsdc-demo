package quantum;

/**
 * Created by Zhao Zhe on 2017/9/9.
 */
public interface QuantumState {
    double[] getState();
    void setState(double[] state);
    int getParticles();
}
