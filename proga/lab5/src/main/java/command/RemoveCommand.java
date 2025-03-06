package command;

import models.MusicBand;
import manager.*;
import utility.*;

import java.util.NoSuchElementException;

public class RemoveCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveCommand(Console console, CollectionManager collectionManager) {
        super("remove", "удалить элемент из коллекции по ID");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        while (argument.isEmpty()) {
            console.println("Введите ID элемента:");
            argument = console.readln().trim();
        }

        int id;
        while (true) {
            try {
                id = Integer.parseInt(argument);
                break;
            } catch (NumberFormatException e) {
                console.printError("Ошибка: ID должен быть числом. Попробуйте снова:");
                argument = console.readln().trim();
            }
        }

        MusicBand oldBand = collectionManager.getById(id);
        if (oldBand == null) {
            console.printError("Ошибка: Группа с ID " + id + " не найдена. Попробуйте снова.");
            return apply("");
        }

        boolean removed = collectionManager.remove(id);
        if (removed) {
            return new ExecutionResponse(true, "Группа с ID " + id + " успешно удалена.");
        } else {
            console.printError("Ошибка: Не удалось удалить группу с ID " + id + ".");
            return apply("");
        }
    }
}



