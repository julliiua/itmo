package command;

import manager.CollectionManager;
import models.MusicBand;
import utility.Ask;
import utility.Console;
import utility.ExecutionResponse;

import java.util.Comparator;
import java.util.Optional;

/**
 * Команда 'add_if_min'. Добавляет элемент в коллекцию, если его продажи меньше минимального значения.
 */
public class AddIfMinCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddIfMinCommand(Console console, CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его продажи меньше минимального значения");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        console.println("Добавление нового элемента, если его продажи меньше минимальных...");

        try {
            console.println("Напишите новый MusicBand:");
            MusicBand newband = Ask.askMusicBand(console, collectionManager.getFreeId());

            if (newband != null && newband.validate()) {
                double minSales = Double.MAX_VALUE;
                for (MusicBand band : collectionManager.getCollection()) {
                    double sales = band.getBestAlbum().getSales();
                    if (sales < minSales) {
                        minSales = sales;
                    }
                }

                if (collectionManager.getCollection().isEmpty() || newband.getBestAlbum().getSales() < minSales) {
                    collectionManager.add(newband);
                    return new ExecutionResponse(true, "Элемент успешно добавлен в коллекцию!");
                } else {
                    return new ExecutionResponse(false, "Элемент НЕ добавлен, так как его продажи не меньше минимальных.");
                }
            } else
                return null;


        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "...");
        }
    }
}
