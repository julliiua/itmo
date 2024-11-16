package attacks;

import ru.ifmo.se.pokemon.*;

public class Thunderbolt extends SpecialMove {
    public Thunderbolt(){
        super(Type.ELECTRIC,90,100);
    }
    protected int count = 0;
    protected boolean isUsed =false;
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <=0.1 && count < 6) {
            count++;
            p.setMod(Stat.HP, -6);
            isUsed = true;
        }
    }
    @Override
    protected String describe(){
        if (isUsed){
            return "использует Thunderbolt парализует соперника";
        }
        return "использует Thunderbolt, не парализуя";
    }
}
