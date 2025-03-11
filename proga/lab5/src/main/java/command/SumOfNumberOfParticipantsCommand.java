package command;

import manager.CollectionManager;
import models.MusicBand;
import utility.*;

/**
 * Команда 'sum_of_number_of_participants'.
 * Выводит сумму значений поля numberOfParticipants для всех элементов коллекции.
 */
public class SumOfNumberOfParticipantsCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public SumOfNumberOfParticipantsCommand(Console console, CollectionManager collectionManager) {
        super("sum_of_number_of_participants", "вывести сумму значений поля numberOfParticipants для всех элементов коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Команда 'sum_of_number_of_participants' не принимает аргументы!");
        }

        int sum = 0;
        for (MusicBand band : collectionManager.getCollection()) {
            sum = sum + band.getNumberOfParticipants();
        }

        return new ExecutionResponse(true, "Сумма значений поля numberOfParticipants: " + sum);
    }
}
