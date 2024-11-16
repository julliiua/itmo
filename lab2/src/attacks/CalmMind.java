package attacks;
import ru.ifmo.se.pokemon.*;
public class CalmMind extends SpecialMove {
    public CalmMind() {
        super(Type.PSYCHIC,0,100);
    }

    protected int count = 0;
    @Override
    protected void applySelfEffects(Pokemon p) {
        if (count < 6) {
            count++;
            p.setMod(Stat.SPECIAL_ATTACK, 1);
            p.setMod(Stat.SPECIAL_DEFENSE, 1);
        }

    }

    @Override
    protected String describe() {
        return "использует Calm Mind и увеличивает специальную атаку и специальную защиту";
    }
}
