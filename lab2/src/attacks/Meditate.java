package attacks;

import ru.ifmo.se.pokemon.*;

public class Meditate extends StatusMove {
    public Meditate(){
        super(Type.PSYCHIC,0,100);
    }

    protected int count = 0;
    @Override
    protected void applySelfEffects(Pokemon p) {
        if (count < 6) {
            count++;
            p.setMod(Stat.ATTACK, 1);
        }

    }
    @Override
    protected String describe() {
        return "использует Meditate и увеличивает атаку";
    }

}
