package attacks;

import ru.ifmo.se.pokemon.*;

public class FocusBlast extends SpecialMove {
    public FocusBlast(){
        super(Type.FIGHTING, 120, 70);
    }
    protected int count =0;
    protected boolean isUsed= false;
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <=0.1 && count < 6) {
            count++;
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
            isUsed = true;
        }
    }
    @Override
    protected String describe(){
        if (isUsed){
            return "использует Focus Blast и снижает специальную защиту соперника";
        }
        return "использует Focus Blast, не снижая специальную защиту";
    }
}
