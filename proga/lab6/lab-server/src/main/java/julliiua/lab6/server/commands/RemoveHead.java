package julliiua.lab6.server.commands;


/**
 * Команда 'remove_head'. Выводит и удаляет первый элемент коллекции.
 */

import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

public class RemoveHead extends Command<NoArgValidator> {
    private final CollectionManager collectionManager;

    public RemoveHead(CollectionManager collectionManager) {
        super("remove_head", "вывести первый элемент коллекции и удалить его", 1, new NoArgValidator());
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse runInternal (String arguments){
        if (collectionManager.getCollection().isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Коллекция пуста, нечего удалять.");
        }
        var firstElement = collectionManager.getCollection().poll();
        return new ExecutionResponse(true, "Удалённый элемент: " + firstElement);

    }
}
