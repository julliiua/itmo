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


}
