package main.java.utility;

import main.java.classes.*;

import java.util.NoSuchElementException;

public class Ask {
    public static class AskBreak extends Exception {}

    public static MusicBand askMusicBand(Console console, int id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("Введите название группы: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }

            var coordinates = askCoordinates(console);
            var numberofparticipants = askNumberoOfParticipants(console);
            var genre = askMusicGenre(console);
            var album = askAlbum(console);

            return new MusicBand(name, coordinates, numberofparticipants, genre, album);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            double x;
            while (true) {
                console.print("Введите координату X: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try { x = Double.parseDouble(line); break; } catch (NumberFormatException e) { }
                }
            }
            Integer y;
            while (true) {
                console.print("Введите координату Y (>-506): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Integer.parseInt(line);
                        if (y > -506) break;
                    } catch (NumberFormatException e) { }
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static int askNumberoOfParticipants(Console console) throws AskBreak {
        try {
            int participants;
            while (true) {
                console.print("Введите количество участников (целое число): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try { participants = Integer.parseInt(line); break; } catch (NumberFormatException e) { }
                }
            }
            return participants;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return 0;
        }
    }

    public static MusicGenre askMusicGenre(Console console) throws AskBreak {
        try {
            MusicGenre genre = null;
            while (true) {
                console.print("Введите жанр (" + genre + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try { genre = MusicGenre.valueOf(line.toUpperCase()); break; }
                    catch (IllegalArgumentException e) { }
                }
            }
            return genre;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Album askAlbum(Console console) throws AskBreak {
        try {
            console.print("Введите название альбома: ");
            String name = console.readln().trim();
            Double sales = null;
            while (true) {
                console.print("Введите количество продаж альбома: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        sales = Double.parseDouble(line);
                        break;
                    } catch (NumberFormatException e) {
                        console.printError("Ошибка: Введите число.");
                    }
                }
            }
            return new Album(name,sales);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
