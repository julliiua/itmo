package julliiua.lab6.server.commands;


import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

/**
 * Команда 'sum_of_number_of_participants'.
 * Выводит сумму значений поля numberOfParticipants для всех элементов коллекции.
 */
public class SumOfNumberOfParticipants extends Command<NoArgValidator>{
    private final CollectionManager collectionManager;
    public SumOfNumberOfParticipants(CollectionManager collectionManager) {
        super("sum_of_number_of_participants", "вывести сумму значений поля numberOfParticipants для всех элементов коллекции", 0, new NoArgValidator());
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse runInternal(String arguments) {
        int sum = 0;
        for (MusicBand band : collectionManager.getCollection()) {
            sum = sum + band.getNumberOfParticipants();
        }

        return new ExecutionResponse(true, "Сумма значений поля numberOfParticipants: " + sum);
    }
}
