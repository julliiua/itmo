package julliiua.lab6.server.commands;

import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

public class Clear extends Command<NoArgValidator> {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию", 0, new NoArgValidator());
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse runInternal(String arguments) {
        collectionManager.clear();
        return new ExecutionResponse(true,"Коллекция очищена!");
    }
}
