package command;


import manager.CollectionManager;
import utility.*;
import classes.MusicBand;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class AddCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddCommand(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */


    @Override
    public ExecutionResponse apply(String argument){
        try {
            if (!argument.isEmpty()) return new ExecutionResponse(false,
                    "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

            console.println("Создание нового MusicBand:");
            MusicBand a = Ask.askMusicBand(console, collectionManager.getFreeId());

            if (a != null && a.validate()) {
                collectionManager.add(a);
                return new ExecutionResponse(true,"MusicBand успешно добавлен!");
            } else
                return new ExecutionResponse(false, "Поля MusicBand не валидны! MusicBand не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "...");
        }
    }
}
