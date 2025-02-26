package command;

import models.MusicBand;
import manager.*;
import utility.*;

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
        if (argument.isEmpty()) {
            console.println("Введите ID элемента:");
            argument = console.readln().trim();
        }

        try {
            int id = Integer.parseInt(argument);
            MusicBand oldBand = collectionManager.getById(id);
            if (oldBand == null) {
                return new ExecutionResponse(false, "Группа с таким ID не найдена.");
            }

            if (collectionManager.remove(id)) {
                return new ExecutionResponse(true, "Группа с ID " + id + " удалена.");
            }
            else{
            return new ExecutionResponse(false, "Некорректные данные. Обновление отменено.");
            } }
        catch(NumberFormatException e){
            return new ExecutionResponse(false, "Некорректный ID. Введите число.");
        }
    }
}


