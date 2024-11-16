package attacks;

import ru.ifmo.se.pokemon.*;

public class EnergyBall extends SpecialMove {
    public EnergyBall(){
        super(Type.GRASS,90 ,100);
    }
    protected int count =0;
    protected boolean isUsed= false;
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <=0.1 && count < 6) {
            count++;
            p.setMod(Stat.SPECIAL_ATTACK, -1);
            isUsed = true;
        }
    }
    @Override
    protected String describe(){
        if (isUsed){
            return "использует Energy Ball и снижает специальную атаку соперника";
        }
        return "использует Energy Ball, не снижая специальную атаку";
    }
}
