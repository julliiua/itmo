package command;

import manager.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class ClearCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public ClearCommand(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Команда 'clear' не принимает аргументы.");
        }

        collectionManager.clear();
        return new ExecutionResponse(true, "Коллекция успешно очищена!");
    }
}

