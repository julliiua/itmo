package utility;

import java.util.Scanner;

/**
 * Интерфейс для работы с консолью: ввод команд и вывод результата.
 */
public interface Console {
    void print(Object obj);
    void println(Object obj);
    String readln();
    boolean isCanReadln();
    void selectFileScanner(Scanner obj);
    void selectConsoleScanner();
    void println(String message);
    void printError(String error);
}
