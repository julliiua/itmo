package attacks;

import ru.ifmo.se.pokemon.*;

public class Spark extends PhysicalMove {
    public Spark(){
        super(Type.ELECTRIC,65,100);
    }
    protected int count = 0;
    protected boolean isUsed =false;
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <=0.3 && count < 6) {
            count++;
            p.setMod(Stat.HP, -1);
            isUsed = true;
        }
    }
    @Override
    protected String describe(){
        if (isUsed){
            return "использует Spark парализует соперника";
        }
        return "использует Spark, не парализуя";
    }
}
