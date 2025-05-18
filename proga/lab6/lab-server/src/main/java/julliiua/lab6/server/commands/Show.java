package julliiua.lab6.server.commands;

import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

/**
 * Класс команды для вывода всех элементов коллекции в строковом представлении.
 */
public class Show extends Command<NoArgValidator> {
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0, new NoArgValidator());
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse runInternal(String arguments) {
        return new ExecutionResponse(true, collectionManager.toString());
    }
}
