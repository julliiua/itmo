package command;

import classes.MusicBand;
import manager.*;
import utility.*;

public class UpdateCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public UpdateCommand(Console console, CollectionManager collectionManager) {
        super("update", "обновить элемент в коллекции по ID");
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

            console.println("Обновление группы с ID " + id);
            MusicBand newBand = Ask.askMusicBand(console, id);

            if (newBand != null && newBand.validate()) {
                collectionManager.update(newBand);
                return new ExecutionResponse(true,"Группа успешно обновлена!");
            } else {
                return new ExecutionResponse(false, "Некорректные данные. Обновление отменено.");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Некорректный ID. Введите число.");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Операция отменена.");
        }
    }

}

