package command;

import manager.*;
import models.MusicBand;
import utility.*;

import java.util.PriorityQueue;

/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;
    private PriorityQueue<MusicBand> collection;

    public SaveCommand(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
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
