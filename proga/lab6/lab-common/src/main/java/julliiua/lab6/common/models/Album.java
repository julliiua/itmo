package julliiua.lab6.common.models;

import julliiua.lab6.common.utility.Validatable;

import java.io.Serial;
import java.io.Serializable;

/**
 * Класс, представляющий музыкальный альбом.
 */
public class Album implements Validatable, Serializable {
    @Serial
    private static final long serialVersionUID = 16L;
    private String name;
    private Double sales;

    /**
     * Конструктор для создания музыкального альбома.
     *
     * @param name  Название альбома
     * @param sales Продажи альбома (должны быть больше 0)
     * @throws IllegalArgumentException если имя пустое или продажи <= 0
     */

    public Album(String name, Double sales) {
        this.name = name;
        this.sales = sales;
    }


    public String getName() {
        return name;
    }

    public Double getSales() {
        return sales;
    }
    public static Album fromString(String data) {
        String[] parts = data.split(",");
        return new Album(parts[0].trim(), (double) Integer.parseInt(parts[1].trim()));
    }
    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (sales == null || sales <= 0) return false;
        return true;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Album album = (Album) obj;
        return name.equals(album.name);
    }

    @Override
    public String toString() {
        return name + "; Продажи=" + sales;
    }
}