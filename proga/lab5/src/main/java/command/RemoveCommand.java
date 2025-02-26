package command;

import manager.*;
import utility.*;

public class RemoveCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveCommand(Console console, CollectionManager collectionManager) {
        super("remove id", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        try {
            int id = Integer.parseInt(argument.trim());
            if (collectionManager.remove(id)) {
                return new ExecutionResponse("Группа с ID " + id + " удалена.");
            } else {
                return new ExecutionResponse(false, "Группа с таким ID не найдена.");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Некорректный ID. Введите число.");
        }
    }
}

