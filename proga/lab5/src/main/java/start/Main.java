package start;

import manager.*;
import utility.*;

public class Main {
    public static void main(String[] args) {
        var console = new StandardConsole();

        String fileName = System.getenv("FILENAME");

        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Ошибка: переменная окружения FILENAME не задана.");
            System.exit(1);
        }
        //Переменная окружения

        DumpManager dumpManager = new DumpManager(fileName, console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);

        if (!collectionManager.load()){
            console.println("Ошибка загрузки коллекции из файла. Проверьте данные.");
        }

        var commandManager = new CommandManager(console, collectionManager);
        var runner = new Runner(console, commandManager);

        console.println("Программа запущена. Введите команду:");
        runner.interactiveMode();
    }
}



