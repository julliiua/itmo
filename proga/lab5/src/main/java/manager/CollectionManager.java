package manager;

import classes.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private final PriorityQueue<MusicBand> collection = new PriorityQueue<>(Comparator.comparing(MusicBand::getName));
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }


    public int getFreeId() {
        return collection.stream().mapToInt(MusicBand::getId).max().orElse(0) + 1;
    }

    public boolean add(MusicBand band) {
        if (containsId(band.getId())) return false;
        collection.add(band);
        return true;
    }

    public boolean update(MusicBand newBand) {
        if (!containsId(newBand.getId())) return false;
        collection.removeIf(band -> band.getId() == newBand.getId());
        collection.add(newBand);
        return true;
    }

    public boolean remove(int id) {
        return collection.removeIf(band -> band.getId() == id);
    }

    public boolean containsId(int id) {
        return collection.stream().anyMatch(band -> band.getId() == id);
    }
    public MusicBand getById(int id) {
        for (MusicBand band : collection) {
            if (band.getId() == id) {
                return band;
            }
        }
        return null;
    }

    public boolean loadCollection() {
        try (BufferedReader br = new BufferedReader(new FileReader("collection.csv"))) {
            collection.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                MusicBand band = new MusicBand(
                        values[1],
                        new Coordinates(Float.parseFloat(values[2]), Integer.parseInt(values[3])),
                        Integer.parseInt(values[5]),
                        MusicGenre.valueOf(values[6].trim().toUpperCase()),
                        new Album(values[7], (double) Integer.parseInt(values[8]))
                );

                collection.add(band);
            }
            lastInitTime = LocalDateTime.now();
            return true;
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Ошибка при загрузке коллекции: " + e.getMessage());
            return false;
        }
    }

    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }
    public PriorityQueue<MusicBand> getCollection() {
        return collection;
    }
    public int getSize() {
        return collection.size();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var band : collection) {
            info.append(band).append("\n\n");
        }
        return info.toString().trim();
    }

}


