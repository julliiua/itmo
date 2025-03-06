package command;

import manager.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'remove_head'. Выводит и удаляет первый элемент коллекции.
 */
public class RemoveHeadCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(Console console, CollectionManager collectionManager) {
        super("remove_head", "вывести первый элемент коллекции и удалить его");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Команда 'remove_head' не принимает аргументы.");
        }

        if (collectionManager.getCollection().isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Коллекция пуста, нечего удалять.");
        }

        var firstElement = collectionManager.getCollection().poll();
        return new ExecutionResponse(true, "Удалённый элемент: " + firstElement);
    }
}
