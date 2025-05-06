package julliiua.lab6.server.managers;

import julliiua.lab6.common.models.*;
import julliiua.lab6.common.models.*;
import julliiua.lab6.common.utility.ExecutionResponse;

import java.io.*;
import java.util.*;

// отвечает за загрузку
public class DumpManager {
    private final String fileName;
    private static DumpManager instance;

    public DumpManager(String fileName) {
        this.fileName = fileName;
    }
    public static DumpManager getInstance() {
        if (instance == null) {
            //Server.logger.info("DumpManager instance created");
            instance = new DumpManager("");
        }
        return instance;
    }

    /**
     * Загружает коллекцию музыкальных групп из файла.
     * @param collection коллекция музыкальных групп
     */
    public ExecutionResponse loadCollection(PriorityQueue<MusicBand> collection) {
        if (collection == null) {
            return new ExecutionResponse(false,"Ошибка: Коллекция пустая" );
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
                    return new ExecutionResponse(false,"Ошибка загрузки строки: " + line);
                }
            }
            return  new ExecutionResponse(true,"OK");
        } catch (FileNotFoundException e) {
            return new ExecutionResponse(false,"Ошибка: Файл не найден.");
        }
    }
    // чтение коллекции

    public ExecutionResponse saveCollection(PriorityQueue<MusicBand> collection) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (MusicBand band : collection) {
                writer.write(band.toCsv() + "\n");
            }
        } catch (IOException e) {
            return new ExecutionResponse(false,"Ошибка при сохранении файла.");
        }
        return new ExecutionResponse(true,"OK");
    }
    //сохранение коллекции
}
