package command;

import manager.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'remove_first'. Удаляет первый элемент из коллекции.
 */
public class RemoveFirstCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveFirstCommand(Console console, CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Команда 'remove_first' не принимает аргументы.");
        }

        if (collectionManager.getCollection().isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Коллекция пуста, нечего удалять.");
        }

        collectionManager.getCollection().poll();
        return new ExecutionResponse(true, "Первый элемент успешно удалён.");
    }
}
