package julliiua.lab6.server.commands;


import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.IdValidator;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

/**
 * Команда 'remove_first'. Удаляет первый элемент из коллекции.
 */
public class RemoveFirst extends Command<NoArgValidator> {
    private final CollectionManager collectionManager;

    public RemoveFirst(CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции", 1, new NoArgValidator());
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse runInternal(String arguments) {
        if (collectionManager.getCollection().isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Коллекция пуста, нечего удалять.");
        }
        collectionManager.getCollection().poll();
        return new ExecutionResponse(true, "Первый элемент успешно удалён.");

    }
}
