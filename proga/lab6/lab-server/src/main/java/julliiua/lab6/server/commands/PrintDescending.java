package julliiua.lab6.server.commands;


import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Команда 'print_descending'.
 * Выводит элементы коллекции в порядке убывания по id.
 */
public class PrintDescending extends Command<NoArgValidator> {
    private final CollectionManager collectionManager;
    public PrintDescending(CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания по id", 0, new NoArgValidator());
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse runInternal(String arguments) {
        PriorityQueue<MusicBand> bands = collectionManager.getCollection();

        ArrayList<MusicBand> sortedBands = new ArrayList<>(bands);
        sortedBands.sort(Collections.reverseOrder());

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Элементы коллекции в порядке убывания:\n");
        for (MusicBand band : sortedBands) {
            resultBuilder.append(band.toString()).append("\n");
        }

        return new ExecutionResponse(true, resultBuilder.toString());
    }

}
