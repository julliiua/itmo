package julliiua.lab6.server.managers;


import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.ExecutionResponse;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

public class CollectionManager {
    private PriorityQueue<MusicBand> collection = new PriorityQueue<>();
    private static volatile CollectionManager instance;
    private static int lastId = 0;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private DumpManager dumpManager;
    String fileName = "C:\\itmo\\proga\\lab6\\collection.csv";

    public CollectionManager() {
        this.fileName = fileName;
        this.dumpManager = new DumpManager();

    }
    public static CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
        }
        return instance;
    }


    public static int getFreeId() {
        return ++lastId;
    }
    public static void setLastId(int k){
        lastId=k;
    }
    // Возвращает первый свободный ID, который на 1 больше, чем максимальный ID в коллекции.
    //Если коллекция пустая, то возвращает 1.

    public boolean add(MusicBand band) {
        collection.add(band);
        return true;
    }


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


    public ExecutionResponse load() {
        ExecutionResponse resp = dumpManager.loadCollection(collection);
        lastInitTime = LocalDateTime.now();
        return resp;
    }

    public void saveCollection() {
        if (!collection.isEmpty()) {
            dumpManager.saveCollection(collection);
        }
    }
    public void clear() {
        collection.clear();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (MusicBand band : collection) {
            info.append(band).append("\n");
        }
        return info.toString().trim();
    }

}
    