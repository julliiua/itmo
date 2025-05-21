package julliiua.lab6.server.commands;

import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Sort extends Command<NoArgValidator> {
    private final CollectionManager collectionManager;
    public Sort(CollectionManager collectionManager) {
        super("sort", "вывести элементы коллекции в порядке возрастания по id", 0, new NoArgValidator());
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse runInternal(String arguments) {
        PriorityQueue<MusicBand> bands = collectionManager.getCollection();

        ArrayList<MusicBand> sortedBands = new ArrayList<>(bands);
        sortedBands.sort(Comparator.naturalOrder());

        StringBuilder result = new StringBuilder();
        result.append("Элементы коллекции в порядке возрастания:\n");
        sortedBands.forEach(band -> result.append(band).append("\n"));

        return new ExecutionResponse(true, result.toString());
    }
}
