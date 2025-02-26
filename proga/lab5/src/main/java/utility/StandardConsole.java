package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Реализация консольного ввода-вывода.
 */
public class StandardConsole implements Console {
    private static final String PROMPT = "$ ";
    private static Scanner fileScanner = null;
    private static final Scanner defaultScanner = new Scanner(System.in);

    /**
     * Выводит obj.toString() в консоль.
     * @param obj Объект для печати.
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Выводит obj.toString() + \n в консоль.
     * @param obj Объект для печати.
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Выводит сообщение об ошибке в стандартный поток ошибок.
     * @param obj Сообщение ошибки.
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
     * Выводит данные в виде таблицы с двумя колонками.
     * @param elementLeft Левый элемент таблицы.
     * @param elementRight Правый элемент таблицы.
     */
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    /**
     * Выводит командную строку (prompt).
     */
    public void prompt() {
        print(PROMPT);
    }

    /**
     * Возвращает строку с символами приглашения.
     * @return Символы приглашения (prompt).
     */
    public String getPrompt() {
        return PROMPT;
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
        System.err.println(error);
    }
}
