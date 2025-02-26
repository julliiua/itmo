package command;

import manager.*;
import utility.*;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class InfoCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public InfoCommand(Console console, CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Команда 'info' не принимает аргументы.");
        }

        StringBuilder info = new StringBuilder();
        info.append("Тип коллекции: PriorityQueue<MusicBand>\n");
        info.append("Дата инициализации: ")
                .append(collectionManager.getLastInitTime() != null ? collectionManager.getLastInitTime() : "Не инициализирована")
                .append("\n");
        info.append("Дата последнего сохранения: ")
                .append(collectionManager.getLastSaveTime() != null ? collectionManager.getLastSaveTime() : "Не сохранялась")
                .append("\n");
        info.append("Количество элементов: ").append(collectionManager.getSize());

        console.println(info.toString());

        return new ExecutionResponse(true,"Информация о коллекции выведена.");
    }
}
