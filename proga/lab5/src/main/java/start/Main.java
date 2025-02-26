package start;

import manager.*;
import utility.*;

public class Main {
    public static void main(String[] args) {
        var console = new StandardConsole();

        String filePath = "collection.csv";

        var dumpManager = new DumpManager(filePath, console);
        var collectionManager = new CollectionManager(dumpManager);

        if (!collectionManager.loadCollection()) {
            console.println("Ошибка загрузки коллекции из файла. Проверьте данные.");
        }

        var commandManager = new CommandManager(console, collectionManager);
        var runner = new add.Runner(console, commandManager);

        console.println("Программа запущена. Введите команду:");

        runner.interactiveMode();


    }
}



