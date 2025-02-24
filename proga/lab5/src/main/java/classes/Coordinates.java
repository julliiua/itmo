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

    @Override
    public String toString() {
        return "x=" + x + "y=" + y;
    }
}