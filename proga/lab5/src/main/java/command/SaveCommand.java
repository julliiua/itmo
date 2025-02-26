package command;

import manager.*;
import utility.*;

/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 */
public class SaveCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public SaveCommand(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Команда 'save' не принимает аргументы!");
        }

        collectionManager.saveCollection();
        return new ExecutionResponse(true,"Коллекция успешно сохранена!");
    }
}
