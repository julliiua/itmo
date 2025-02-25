package main.java.utility;

import main.java.classes.*;
import au.com.bytecode.opencsv.*;

import java.io.*;
import java.util.*;

public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    private String collection2CSV(Collection<MusicBand> collection) {
        try {
            StringWriter sw = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(sw, ';');
            for (var e : collection) {
                csvWriter.writeNext(MusicBand.toArray(e));
            }
            return sw.toString();
        } catch (Exception e) {
            console.printError("Ошибка сериализации");
            return null;
        }
    }

    public void writeCollection(Collection<MusicBand> collection) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName))) {
            var csv = collection2CSV(collection);
            if (csv == null) return;
            writer.write(csv);
            writer.flush();
            console.println("Коллекция успешно сохранена в файл!");
        } catch (IOException e) {
            console.printError("Ошибка сохранения файла");
        }
    }

    private LinkedList<MusicBand> CSV2collection(String s) {
        try {
            CSVReader csvReader = new CSVReader(new StringReader(s), ';');
            LinkedList<MusicBand> collection = new LinkedList<>();
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                MusicBand band = MusicBand.fromArray(record);
                if (band != null) {
                    collection.add(band);
                } else {
                    console.printError("Некорректные данные в файле");
                }
            }
            return collection;
        } catch (Exception e) {
            console.printError("Ошибка десериализации");
            return null;
        }
    }

    public void readCollection(Collection<MusicBand> collection) {
        if (fileName != null && !fileName.isEmpty()) {
            try (Scanner fileReader = new Scanner(new File(fileName))) {
                StringBuilder s = new StringBuilder();
                while (fileReader.hasNextLine()) {
                    s.append(fileReader.nextLine()).append("\n");
                }
                collection.clear();
                LinkedList<MusicBand> loaded = CSV2collection(s.toString());
                if (loaded != null) {
                    collection.addAll(loaded);
                    console.println("Коллекция успешно загружена!");
                } else {
                    console.printError("Файл не содержит валидных данных!");
                }
            } catch (FileNotFoundException e) {
                console.printError("Файл не найден!");
            }
        } else {
            console.printError("Файл не указан!");
        }
    }
}

