package main.java.add;


import main.java.classes.*;
import main.java.utility.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<MusicBand> musicBands = new ArrayList<>();

    public static void main(String[] args) throws Ask.AskBreak {
        var console = new StandardConsole();
        musicBands.add(Ask.askMusicBand(console, 100));

        for (var band : musicBands) {
            System.out.println(band);
        }
    }
}
