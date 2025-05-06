package julliiua.lab6.common.models;

import julliiua.lab6.common.utility.Validatable;
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
    public double getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
    public static Coordinates fromString(String data) {
        String[] parts = data.replace("(", "").replace(")", "").split(",");
        return new Coordinates(Double.parseDouble(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }
    @Override
    public boolean validate() {
        if (y == null || y <= -506) return false;
        return true;
    }


    @Override
    public String toString() {
        return x + ";" + y;
    }
}