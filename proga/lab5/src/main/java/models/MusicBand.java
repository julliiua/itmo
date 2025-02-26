package models;


import utility.Element;
import utility.Validatable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс, представляющий музыкальную группу.
 */
public class MusicBand extends Element implements Validatable, Serializable {
    private static int lastId = 0;
    private final int id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate = LocalDateTime.now();;
    private int numberOfParticipants;
    private MusicGenre genre;
    private Album bestAlbum;

    /**
     * Конструктор для создания объекта MusicBand.
     *
     * @param name                 Название группы
     * @param coordinates          Координаты группы
     * @param numberOfParticipants Количество участников
     * @param genre                Музыкальный жанр группы
     * @param bestAlbum            Лучший альбом группы
     */


    /**
     * Конструктор для создания объекта MusicBand.
     */
    public MusicBand(String name, Coordinates coordinates, int numberOfParticipants, MusicGenre genre, Album bestAlbum) {
        this.id = ++lastId;
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    /**
     * Конструктор для загрузки объекта из файла (id уже существует).
     */
    public MusicBand(int id, String name, Coordinates coordinates, int numberOfParticipants, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
        if (id > lastId) lastId = id;
    }
    /**
     * Метод для создания объекта из массива строк (чтение CSV).
     */
    public static MusicBand fromArray(String[] data) {
        try {
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            Coordinates coordinates = Coordinates.fromString(data[2]);
            int numberOfParticipants = Integer.parseInt(data[3]);
            MusicGenre genre = data[4].equals("null") ? null : MusicGenre.valueOf(data[4]);
            Album bestAlbum = data[5].equals("null") ? null : new Album(data[5], (double) Integer.parseInt(data[6]));
            return new MusicBand(id, name, coordinates, numberOfParticipants, genre, bestAlbum);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Преобразование объекта в массив строк (запись CSV).
     */
    public static String[] toArray(MusicBand band) {
        return new String[]{
                String.valueOf(band.id),
                band.name,
                band.coordinates.toString(),
                String.valueOf(band.numberOfParticipants),
                band.genre == null ? "null" : band.genre.toString(),
                band.bestAlbum == null ? "null" : band.bestAlbum.getName(),
                band.bestAlbum == null ? "null" : String.valueOf(band.bestAlbum.getSales())
        };
    }
    public int getId() {
        return id;
    }
    public String toCsv() {
        return id + "," + name + "," + coordinates.getX() + "," + coordinates.getY() + ","
                + creationDate + "," + numberOfParticipants + "," + genre + ","
                + (bestAlbum != null ? bestAlbum.getName() + "," + bestAlbum.getSales() : "null,null");
    }

    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public int getNumberOfParticipants() { return numberOfParticipants; }
    public MusicGenre getGenre() { return genre; }
    public Album getBestAlbum() { return bestAlbum; }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, numberOfParticipants, genre);
    }

    @Override
    public int compareTo(Element element) {
        return Integer.compare(this.getId(), element.getId());
    }

    @Override
    public boolean validate() {
        if (id <= 0) {
            return false;
        }
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (numberOfParticipants <= 0) return false;
        return true;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", numberOfParticipants=" + numberOfParticipants +
                ", genre=" + genre +
                ", bestAlbum=" + bestAlbum +
                ", creationDate=" + creationDate ;
    }


}
