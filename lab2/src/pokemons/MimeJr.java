package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class MimeJr extends Pokemon {
    public MimeJr(String name, int level){
        super(name, level);
        setType(Type.PSYCHIC, Type.FAIRY);
        setStats(20,25,45,70,90,60);
        setMove(new ThunderWave(),new Thunderbolt(), new Meditate());
    }
}
