package attacks;
import ru.ifmo.se.pokemon.*;
public class AncientPower extends SpecialMove {
    public AncientPower() {
        super(Type.ROCK, 60, 100);
    }

    protected int count = 0;
    protected boolean isUsed = false;

    @Override
    protected void applySelfEffects(Pokemon p) {
        if (Math.random() <= 0.1 && count < 6) {
            count++;
            isUsed = true;
            p.setMod(Stat.HP, 1);
            p.setMod(Stat.ATTACK, 1);
            p.setMod(Stat.DEFENSE, 1);
            p.setMod(Stat.SPEED, 1);
            p.setMod(Stat.SPECIAL_ATTACK, 1);
            p.setMod(Stat.SPECIAL_DEFENSE, 1);
            p.setMod(Stat.ACCURACY, 1);
            p.setMod(Stat.EVASION, 1);
        }

    }

    @Override
    protected String describe() {
        if (isUsed) {
            return "использует Ancient Power и увеличивает свои характеристики";
        }
        return "использует Ancient Power, не меняя характеристик";
    }
}
