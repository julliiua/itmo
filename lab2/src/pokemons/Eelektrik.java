package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Eelektrik extends Tynamo{
    public Eelektrik(String name, int level){
        super(name,level);
        setStats(65,85,70,75,70,40);
        setMove(new Spark());
    }
}
