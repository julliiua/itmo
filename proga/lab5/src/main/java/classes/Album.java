package main.java.classes;

import main.java.utility.Validatable;

/**
 * Класс, представляющий музыкальный альбом.
 */
public class Album implements Validatable {
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

    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (sales == null || sales <= 0) return false;
        return true;
    }

    public Album(String s) {
        String[] parts = s.split(";");
        this.name = parts[0];
        this.sales = Double.parseDouble(parts[1]);
    }

    @Override
    public String toString() {
        return name + ";" + sales;
    }
}