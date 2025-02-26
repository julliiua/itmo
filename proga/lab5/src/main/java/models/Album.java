package classes;

import utility.Validatable;

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
    public String toString() {
        return name + "; Продажи=" + sales;
    }
}