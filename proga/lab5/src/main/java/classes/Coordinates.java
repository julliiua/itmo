package main.java.classes;

import main.java.utility.Validatable;

/**
 * Класс, представляющий координаты музыкальной группы.
 */
public class Coordinates implements Validatable {
    private double x;
    private Integer y;

    /**
     * Конструктор для создания координат.
     *
     * @param x Координата X
     * @param y Координата Y (должна быть больше -506)
     */

    public Coordinates(double x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean validate() {
        if (y == null || y <= -506) return false;
        return true;
    }

    public Coordinates(String s) {
        String[] parts = s.split(";");
        this.x = Double.parseDouble(parts[1]);
        this.y = Integer.parseInt(parts[0]);
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }
}