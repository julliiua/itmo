import pokemons.*;
import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Sigilyph p1 = new Sigilyph("Летающий шар", 3);
        MimeJr p2 = new MimeJr("Мини мим", 5);
        MrMime p3 = new MrMime("Мим",7);
        Tynamo p4 = new Tynamo("Белая рыбка",2);
        Eelektross p5 = new Eelektross("Водный червь", 8);
        Eelektrik p6 = new Eelektrik("Змея с руками", 7);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}