package julliiua.lab6.common.utility;

/**
 * Абстрактный класс для элементов коллекции.
 */
public abstract class Element implements Comparable<Element>, Validatable {
    /**
     * Получить ID элемента.
     * @return ID элемента
     */
    public abstract int getId();
}
