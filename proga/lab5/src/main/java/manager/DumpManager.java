package manager;

import classes.*;
import utility.Console;

import java.io.*;
import java.util.*;

public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    public boolean loadCollection(PriorityQueue<MusicBand> collection) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            collection.clear();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                if (values.length < 8) continue;

                try {
                    MusicBand band = new MusicBand(
                            values[1],
                            new Coordinates(Float.parseFloat(values[2]), Integer.parseInt(values[3])),
                            Integer.parseInt(values[5]),
                            MusicGenre.valueOf(values[6].trim().toUpperCase()),
                            new Album(values[7], (double) Integer.parseInt(values[8]))
                    );
                    collection.add(band);
                } catch (Exception e) {
                    console.printError("Ошибка загрузки строки: " + line);
                }
            }
            console.println("Коллекция загружена из файла.");
            return true;
        } catch (FileNotFoundException e) {
            console.printError("Ошибка: Файл не найден.");
            return false;
        }
    }

    public void saveCollection(PriorityQueue<MusicBand> collection) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (MusicBand band : collection) {
                writer.write(band.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            console.printError("Ошибка при сохранении файла.");
        }
    }

    public void writeCollection(PriorityQueue<MusicBand> collection) {
        saveCollection(collection);
    }

    public void readCollection(PriorityQueue<MusicBand> collection) {
        loadCollection(collection);
    }
}
