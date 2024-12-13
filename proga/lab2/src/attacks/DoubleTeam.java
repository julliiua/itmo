package attacks;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam(){
        super(Type.NORMAL,0,100);
    }
    protected int count = 0;
    @Override
    protected void applySelfEffects(Pokemon p) {
        if (count < 6) {
            count++;
            p.setMod(Stat.EVASION, 1);
        }
    }
    @Override
    protected String describe() {
        return "использует Double Team, увеличивая уклонение";
    }
}
