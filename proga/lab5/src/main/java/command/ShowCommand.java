package command;

import models.MusicBand;
import manager.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Команда 'show'. Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */
public class ShowCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public ShowCommand(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Команда 'show' не принимает аргументы!");
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
        return new ExecutionResponse(true,"Все элементы коллекции выведены.");
    }
}
