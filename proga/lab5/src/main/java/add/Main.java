package main.java.add;


import main.java.classes.*;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        PriorityQueue<MusicBand> musicBands = new PriorityQueue<>();

        musicBands.add(new MusicBand("Pink Floyd", new Coordinates(10.5, 20), 5, MusicGenre.POP, new Album("The Wall",30.0)));
        musicBands.add(new MusicBand("Nirvana", new Coordinates(15.0, -100), 3, MusicGenre.RAP, new Album("Nevermind",45.0)));

        System.out.println("Содержимое коллекции:");
        for (MusicBand band : musicBands) {
            System.out.println(band);
        }
    }
}