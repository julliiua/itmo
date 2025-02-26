package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Реализация консольного ввода-вывода.
 */
public class StandardConsole implements Console {
    private static Scanner fileScanner = null;
    private static final Scanner defaultScanner = new Scanner(System.in);

    /**
     * Выводит obj в консоль
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Выводит obj.toString() + \n в консоль.
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Выводит сообщение об ошибке в стандартный поток ошибок.
     */
    public void printError(Object obj) {
        System.err.println("Ошибка: " + obj);
    }

    /**
     * Считывает строку из ввода.
     * @return Введённая строка.
     * @throws NoSuchElementException если нет входных данных.
     * @throws IllegalStateException если Scanner закрыт.
     */
    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner != null ? fileScanner : defaultScanner).nextLine();
    }

    /**
     * Проверяет, есть ли ещё строки для считывания.
     * @return true, если есть ещё строки.
     */
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner : defaultScanner).hasNextLine();
    }


    /**
     * Устанавливает файл в качестве источника ввода.
     * @param scanner Новый Scanner для чтения данных из файла.
     */
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }

    /**
     * Возвращает ввод с консоли вместо файла.
     */
    public void selectConsoleScanner() {
        fileScanner = null;
    }


    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String error) {
        System.err.println("Ошибка: " + error);
    }
}
