package attacker;

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
            Attack.EntangleMeasureAttack(payload);
        }

    }

}
