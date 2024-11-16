package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Sigilyph extends Pokemon {
    public Sigilyph(String name, int level){
        super(name, level);
        setType(Type.PSYCHIC, Type.FLYING);
        setStats(72,58,80,103,80,97);
        setMove(new AncientPower(), new EnergyBall(), new Psywave(), new CalmMind());
    }
}
