package julliiua.lab6.server.managers;

import com.opencsv.CSVWriter;
import julliiua.lab6.common.models.*;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.server.Server;

import java.io.*;
import java.util.*;

// отвечает за загрузку
public class DumpManager {
    private final String filePath;
    private static volatile DumpManager instance;

    /**
     * Конструктор для создания объекта DumpManager.
     */
    public DumpManager() {
        this.filePath = "C:\\itmo\\proga\\lab6\\collection.csv";
        // Проверка наличия и корректности переменной окружения
        if (filePath == null) {
            Server.logger.severe("Environment variable FILENAME not found!");
            System.exit(1);
        } else if (filePath.isEmpty()) {
            Server.logger.severe("Environment variable FILENAME does not contain a file path!");
            System.exit(1);
        } else if (!filePath.endsWith(".csv")) {
            Server.logger.severe("The file must be in .csv format!");
            System.exit(1);
        } else if (!new File(filePath).exists()) {
            Server.logger.severe("The file at the specified path was not found!");
            System.exit(1);
        }
    }

    public static DumpManager getInstance() {
        if (instance == null) {
            instance = new DumpManager();
            return instance;
        }
        return null;
    }
    /**
     * Загружает коллекцию музыкальных групп из файла.
     * @param collection коллекция музыкальных групп
     */
    public ExecutionResponse loadCollection(PriorityQueue<MusicBand> collection) {
        if (collection == null) {
            return new ExecutionResponse(false,"Ошибка: Коллекция пустая" );
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return new ExecutionResponse(false, "Ошибка: Файл не найден.");
        }
        int k=0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;


                String[] values = line.replace("\"", "").split(",");
                if (values.length < 8) continue;
                try {
                    MusicBand band = new MusicBand(
                            Integer.parseInt(values[0]),
                            values[1],
                            new Coordinates(Double.parseDouble(values[2]), Integer.parseInt(values[3])),
                            Integer.parseInt(values[5]),
                            MusicGenre.valueOf(values[6].trim().toUpperCase()),
                            new Album(values[7], Double.parseDouble(values[8]))
                    );
                    collection.add(band);
                    k += 1;
                } catch (Exception e) {
                    return new ExecutionResponse(false,"Ошибка загрузки строки: " + line + " " + e);
                }
            }
            CollectionManager.setLastId(k);
            return  new ExecutionResponse(true,"OK");
        } catch (FileNotFoundException e) {
            return new ExecutionResponse(false,"Ошибка: Файл не найден.");
        }
    }
    // чтение коллекции

    public ExecutionResponse saveCollection(PriorityQueue<MusicBand> collection) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            for (MusicBand band : collection) {
                writer.writeNext(new String[]{band.toCsv().toString()});
            }
            writer.close();
            return new ExecutionResponse(true, "Коллекция успешно сохранена в файл!");
        } catch (IOException e) {
            return new ExecutionResponse(false, "Произошла ошибка при записи коллекции в файл!");
        }
    }
    //сохранение коллекции

}

