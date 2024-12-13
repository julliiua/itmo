package attacks;
import ru.ifmo.se.pokemon.*;
public class ChargeBeam extends SpecialMove {
    public ChargeBeam(){
        super(Type.ELECTRIC,50,90);
    }
    protected int count = 0;
    protected boolean isUsed = false;
    @Override
    protected void applySelfEffects(Pokemon p) {
        if (Math.random() <=0.7 && count < 6) {
            count++;
            p.setMod(Stat.SPECIAL_ATTACK, 1);
            isUsed = true;
        }
    }
    @Override
    protected String describe() {
        if (isUsed) {
            return "использует Charge Beam и увеличивает специальную атаку";
        }
        return "использует Charge Beam, не меняя характеристику специальной атаки";
    }
}
