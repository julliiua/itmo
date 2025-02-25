package main.java.classes;


import main.java.utility.Element;
import main.java.utility.Validatable;

import java.io.Serializable;
import java.util.Date;

/**
 * Класс, представляющий музыкальную группу.
 */
public class MusicBand extends Element implements Validatable, Serializable {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private int numberOfParticipants;
    private MusicGenre genre;
    private Album bestAlbum;

    /**
     * Конструктор для создания объекта MusicBand.
     *
     * @param name Название группы
     * @param coordinates Координаты группы
     * @param numberOfParticipants Количество участников
     * @param genre Музыкальный жанр группы
     * @param bestAlbum Лучший альбом группы
     */

    public MusicBand(String name, Coordinates coordinates, int numberOfParticipants, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }
    public static MusicBand fromArray(String[] data) {
        try {
            Long id = Long.parseLong(data[0]);
            String name = data[1];
            Coordinates coordinates = new Coordinates(data[2]);
            int numberOfParticipants = Integer.parseInt(data[3]);
            MusicGenre genre = data[4].equals("null") ? null : MusicGenre.valueOf(data[4]);
            Album bestAlbum = data[5].equals("null") ? null : new Album(data[5]);
            return new MusicBand(name, coordinates, numberOfParticipants, genre, bestAlbum);
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] toArray(MusicBand band) {
        return new String[]{
                band.id.toString(),
                band.name,
                band.coordinates.toString(),
                String.valueOf(band.numberOfParticipants),
                band.genre == null ? "null" : band.genre.toString(),
                band.bestAlbum == null ? "null" : band.bestAlbum.toString()
        };
    }
    @Override
    public int getId() {
        return id.intValue();
    }

    @Override
    public int compareTo(Element element) {
        return Integer.compare(this.getId(), element.getId());
    }

    @Override
    public boolean validate() {
        if (id == null || id <= 0) return false;
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
