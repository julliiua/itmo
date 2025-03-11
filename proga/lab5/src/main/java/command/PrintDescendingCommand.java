package command;

import manager.CollectionManager;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Команда 'print_descending'.
 * Выводит элементы коллекции в порядке убывания по id.
 */
public class PrintDescendingCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintDescendingCommand(Console console, CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания по id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Команда 'print_descending' не принимает аргументы!");
        }

        PriorityQueue<MusicBand> bands = collectionManager.getCollection();

        if (bands.isEmpty()) {
            return new ExecutionResponse(true, "Коллекция пуста.");
        }

        console.println("Элементы коллекции:");
        ArrayList<MusicBand> sortedBands = new ArrayList<>();
        for(var e : collectionManager.getCollection()){
            sortedBands.add(e);
        }
        sortedBands.sort(MusicBand::compareTo);

        for (MusicBand band : sortedBands) {
            console.println(band);
        }
       return new ExecutionResponse(true, "Все элементы коллекции выведены в порядке убывания по id.");
    }
}

