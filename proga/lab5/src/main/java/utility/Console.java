package main.java.utility;

import java.util.Scanner;

/**
 * Интерфейс для работы с консолью: ввод команд и вывод результата.
 */
public interface Console {
    void print(Object obj);
    void println(Object obj);
    String readln();
    boolean isCanReadln();
    void printError(Object obj);
    void printTable(Object obj1, Object obj2);
    void prompt();
    String getPrompt();
    void selectFileScanner(Scanner obj);
    void selectConsoleScanner();
}
