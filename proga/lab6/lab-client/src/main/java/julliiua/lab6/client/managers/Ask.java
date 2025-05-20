package julliiua.lab6.client.managers;

import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.client.utility.Console;
import java.util.NoSuchElementException;

import julliiua.lab6.common.models.*;

public class Ask {
    public static class AskBreak extends Exception {
    }
    /**
     * Исключение, выбрасываемое для прерывания ввода.
     */
    public static class Breaker extends Exception { }
    /**
     * Исключение, выбрасываемое при некорректном вводе.
     */
    public static class IllegalInputException extends IllegalArgumentException {
        /**
         * Конструктор исключения с сообщением.
         * @param message Сообщение об ошибке.
         */
        public IllegalInputException(String message) {
            super(message);
        }
    }
    public static MusicBand askMusicBand(Console console) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("Введите название группы: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
                console.printError("название не может быть пустым.");
            }
            Coordinates coordinates = askCoordinates(console);
            Integer numberOfParticipants = askNumberOfParticipants(console);
            MusicGenre genre = askMusicGenre(console);
            Album album = askAlbum(console);

            return new MusicBand(1, name, coordinates, numberOfParticipants, genre, album);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("чтения данных. Проверьте ввод и повторите попытку.");
            return null;
        }
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            Double x = null;
            while (x == null) {
                console.print("Введите координату X: ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    x = Double.parseDouble(line);
                } catch (NumberFormatException e) {
                    console.printError("X должно быть числом.");
                }
            }

            Integer y = null;
            while (y == null) {
                console.print("Введите координату Y (должно быть > -506): ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    int tempY = Integer.parseInt(line);
                    if (tempY > -506) {
                        y = tempY;
                    } else {
                        console.printError("Y должно быть больше -506.");
                    }
                } catch (NumberFormatException e) {
                    console.printError("Y должно быть целым числом.");
                }
            }

            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError(" чтения координат.");
            return null;
        }
    }

    public static int askNumberOfParticipants(Console console) throws AskBreak {
        try {
            Integer participants = null;
            while (participants == null) {
                console.print("Введите количество участников (целое число): ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    participants = Integer.parseInt(line);
                    if (participants <= 0) {
                        console.printError("количество участников должно быть положительным числом.");
                        participants = null;
                    }
                } catch (NumberFormatException e) {
                    console.printError("введите целое число.");
                }
            }
            return participants;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("чтения числа участников.");
            return 0;
        }
    }

    public static MusicGenre askMusicGenre(Console console) throws AskBreak {
        try {
            MusicGenre genre = null;
            while (genre == null) {
                console.print("Введите жанр (RAP, HIP_HOP, BLUES, POP, POST_PUNK): ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    genre = MusicGenre.valueOf(line.toUpperCase());
                } catch (IllegalArgumentException e) {
                    console.printError("жанр должен быть одним из следующих: RAP, HIP_HOP, BLUES, POP, POST_PUNK.");
                    console.print("Введите жанр (RAP, HIP_HOP, BLUES, POP, POST_PUNK): ");
                }
            }
            return genre;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("чтения жанра.");
            return null;
        }
    }

    public static Album askAlbum(Console console) throws AskBreak {
        try {
            console.print("Введите название альбома: ");
            String name = console.readln().trim();
            if (name.equals("exit")) throw new AskBreak();
            if (name.isEmpty()) {
                console.printError("название альбома не может быть пустым.");

                return askAlbum(console);

            }

            Double sales = null;
            while (sales == null) {
                console.print("Введите количество продаж альбома (число): ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    sales = Double.parseDouble(line);
                } catch (NumberFormatException e) {
                    console.printError("введите число.");
                }
            }

            return new Album(name, sales);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("чтения данных об альбоме.");
            return null;
        }
    }

    public static Album askBestAlbum(Console console) throws AskBreak {
        try {
            console.print("Введите название альбома: ");
            String name = console.readln().trim();
            if (name.equals("exit")) throw new AskBreak();
            if (name.isEmpty()) {
                console.printError("название альбома не может быть пустым.");

                return askBestAlbum(console);
            }
            return new Album(name, 1.0);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("чтения данных об альбоме.");
            return null;
        }

    }
}