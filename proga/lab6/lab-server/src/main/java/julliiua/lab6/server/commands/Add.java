package julliiua.lab6.server.commands;


import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;
import julliiua.lab6.server.utility.AskCommand;

/**
 * Класс, представляющий команду добавления нового продукта в коллекцию.
 */
public class Add extends AskCommand<NoArgValidator> {
    private final CollectionManager collectionManager;
    /**
     * Конструктор для создания объекта Add.
     * @param collectionManager менеджер коллекции для управления продуктами
     */
    public Add(CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию", new NoArgValidator());
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse  runInternal(MusicBand band) {
        System.out.println(band);
        CollectionManager.getInstance().add(band);
        return new ExecutionResponse(true,"Элемент успешно добавлен в коллекцию!");
    }
}
