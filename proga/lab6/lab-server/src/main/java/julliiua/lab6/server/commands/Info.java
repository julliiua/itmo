package julliiua.lab6.server.commands;


import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CollectionManager;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command<NoArgValidator> {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции", 0, new NoArgValidator());
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */


    public ExecutionResponse runInternal(String arguments) {

        String s = "Сведения о коллекции:\n";
        s+=" Тип: " + collectionManager.getCollection().getClass().toString()+"\n";
        s+=" Количество элементов: " + collectionManager.getCollection().size()+"\n";
        return new ExecutionResponse(true, s+"\n" + "Информация о коллекции выведена.");
    }
}
