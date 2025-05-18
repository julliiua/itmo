package julliiua.lab6.common.models;


import julliiua.lab6.common.utility.Element;
import julliiua.lab6.common.utility.Validatable;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс, представляющий музыкальную группу.
 */
public class MusicBand extends Element implements Validatable, Serializable {
    @Serial
    private static final long serialVersionUID = 14L;
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
    public int compareBySales(MusicBand other) {
        return this.bestAlbum.getSales().compareTo(other.bestAlbum.getSales());
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
