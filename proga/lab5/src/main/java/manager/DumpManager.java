package manager;

import models.*;
import utility.Console;

import java.io.*;
import java.io.FileWriter;
import java.util.PriorityQueue;
import java.util.Scanner;


public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
        if (this.fileName == null || this.fileName.isEmpty()) {
            console.printError("Ошибка: переменная окружения FILENAME не задана.");
            System.exit(1);
        }
    }

    public boolean loadCollection(PriorityQueue<MusicBand> collection) {
        if (collection == null) {
            console.printError("Ошибка: Коллекция пустая");
            return false;
        }

        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;


                String[] values = line.split(",");
                if (values.length < 8) continue;

                try {
                    MusicBand band = new MusicBand(
                            values[1],
                            new Coordinates(Double.parseDouble(values[2]), Integer.parseInt(values[3])),
                            Integer.parseInt(values[5]),
                            MusicGenre.valueOf(values[6].trim().toUpperCase()),
                            new Album(values[7], Double.parseDouble(values[8]))
                    );
                    collection.add(band);
                } catch (Exception e) {
                    console.printError("Ошибка загрузки строки: " + line);
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            console.printError("Ошибка: Файл не найден.");
            return false;
        }
    }
    // чтение коллекции

    public void saveCollection(PriorityQueue<MusicBand> collection) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (MusicBand band : collection) {
                writer.write(band.toCsv() + "\n");
            }
        } catch (IOException e) {
            console.printError("Ошибка при сохранении файла.");
        }
    }
    //сохранение коллекции
}

