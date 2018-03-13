package attacker;

import util.Payload;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhao Zhe on 2017/10/9.
 */
public class Attacker {
    private String strategy;
    public Attacker(String strategy){
        this.strategy = strategy;
    }

    public void attack(Map<String,List> payload){

        if(this.strategy.equals(AttackStrategy.ENTANGLE_AND_MEASURE)){
            List<Double> fac = payload.get(Payload.ATTACK_FAC);
            Attack.entangleMeasureAttack(payload,fac.get(0),fac.get(1));
        }else if(this.strategy.equals(AttackStrategy.MODIFY)){
            Attack.modifyAttack(payload);

        }else if(this.strategy.equals(AttackStrategy.RESEND)){
            Attack.resendAttack(payload);
        }else if(this.strategy.equals(AttackStrategy.MEASURE_AND_RESEND)){
            Attack.measureResendAttack(payload);
        }

    }


}
