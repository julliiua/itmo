package manager;

import models.*;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class CollectionManager {
    private PriorityQueue<MusicBand> collection = new PriorityQueue<>();
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
    //Позволяют получить время последней загрузки и сохранения.

    public int getFreeId() {
        return collection.stream().mapToInt(MusicBand::getId).max().orElse(0) + 1;
    }
    // Возвращает первый свободный ID, который на 1 больше, чем максимальный ID в коллекции.
    //Если коллекция пустая, то возвращает 1.

    public boolean add(MusicBand band) {
        if (containsId(band.getId())) return false;
        collection.add(band);
        return true;
    }
    // Елси есть группа с таким id, то не добавляет

    public boolean update(MusicBand newBand) {
        if (!containsId(newBand.getId())) return false;
        collection.removeIf(band -> band.getId() == newBand.getId());
        collection.add(newBand);
        return true;
    }
    // Если есть группа с таким id, то удаляет старую версию и добавляет новую

    public boolean remove(int id) {
        return collection.removeIf(band -> band.getId() == id);
    }
    //Удаляет группу по id

    public boolean containsId(int id) {
        return collection.stream().anyMatch(band -> band.getId() == id);
    }
    //Проверка на существцющий айди

    public MusicBand getById(int id) {
        for (MusicBand band : collection) {
            if (band.getId() == id) {
                return band;
            }
        }
        return null;
    }
    // Поиск по id

    public PriorityQueue<MusicBand> getCollection() {
        return collection;
    }
    public int getSize() {
        return collection.size();
    }

    public boolean load() {
        dumpManager.loadCollection(collection);
        return true;
    }

    public void saveCollection() {
        dumpManager.saveCollection(collection);
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var band : collection) {
            info.append(band).append("\n");
        }
        return info.toString().trim();
    }
    public void clear() {
        collection.clear();
    }



}


