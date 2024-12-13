package attacks;

import ru.ifmo.se.pokemon.*;

public class ThunderWave extends StatusMove {
    public ThunderWave(){
        super(Type.ELECTRIC,0,90);
    }
    protected int count = 0;
    @Override
    protected void applyOppEffects(Pokemon p){
        if (count < 6) {
            count++;
            p.setMod(Stat.HP, -6);
        }
    }

    @Override
    protected String describe(){
        return "использует Thunder Wave парализуя противника";
    }
}
