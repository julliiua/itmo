package command;

import models.MusicBand;
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
        while (argument.isEmpty()) {
            console.println("Введите ID элемента для обновления:");
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

        MusicBand oldBand;
        while (true) {
            oldBand = collectionManager.getById(id);
            if (oldBand != null) {
                break;
            }
            console.printError("Ошибка: Группа с ID " + id + " не найдена. Введите корректный ID:");
            argument = console.readln().trim();
            try {
                id = Integer.parseInt(argument);
            } catch (NumberFormatException e) {
                console.printError("Ошибка: ID должен быть числом. Попробуйте снова:");
            }
        }

        console.println("Обновление группы с ID " + id);
        MusicBand newBand;
        while (true) {
            try {
                newBand = Ask.askMusicBand(console, id);
                if (newBand != null && newBand.validate()) {
                    break;
                } else {
                    console.printError("Ошибка: Некорректные данные. Повторите ввод.");
                }
            } catch (Ask.AskBreak e) {
                return new ExecutionResponse(false, "...");
            }
        }

        collectionManager.update(newBand);
        return new ExecutionResponse(true, "Группа успешно обновлена!");
    }

}

