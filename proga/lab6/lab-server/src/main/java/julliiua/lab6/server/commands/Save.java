package julliiua.lab6.server.commands;

import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

public class Save extends Command<NoArgValidator> {
    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл", 0, new NoArgValidator());
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse runInternal(String arguments) {
        collectionManager.saveCollection();
        return new ExecutionResponse(true,"Коллекция успешно сохранена в файл.");
    }
}