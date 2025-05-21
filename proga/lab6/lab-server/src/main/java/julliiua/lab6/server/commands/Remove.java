package julliiua.lab6.server.commands;


import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.IdValidator;
import julliiua.lab6.server.managers.CollectionManager;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 */
public class Remove extends Command<IdValidator> {
    private final CollectionManager collectionManager;

    public Remove(CollectionManager collectionManager) {
        super("remove", "удалить элемент из коллекции по ID", 1, new IdValidator());
        this.collectionManager = collectionManager;
}
    @Override
    public ExecutionResponse runInternal(String args) {
        int id = -1;
        if (collectionManager.getById(id) == null )
            return new ExecutionResponse(false, "Не существующий ID");
        collectionManager.remove(id);
        return new ExecutionResponse(true,"Группа с ID \" + id + \" успешно удалена.");
    }
}
