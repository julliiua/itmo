package attacks;

import ru.ifmo.se.pokemon.*;

public class Psywave extends SpecialMove {
    public Psywave(){
        super(Type.PSYCHIC,0,100);
    }
    protected int count = 0;
    @Override
    protected void applyOppEffects(Pokemon p){
        if (count<6){
            count++;
            p.setMod(Stat.HP, (int) (Math.random() + 0.5) * p.getLevel());
        }
    }

    @Override
    protected String describe() {
        return "использует Psywave, увеличивая здоровье";
    }
}
