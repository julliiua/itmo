package manager;

import command.*;
import utility.*;

import java.util.*;

/**
 * Менеджер команд: отвечает за регистрацию и выполнение команд.
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Конструктор CommandManager.
     * Регистрирует все доступные команды.
     */
    public CommandManager(Console console, CollectionManager collectionManager) {
        register(new AddCommand(console, collectionManager));
        register(new UpdateCommand(console, collectionManager));
        register(new RemoveCommand(console, collectionManager));
        register(new HelpCommand(console, this));
        // this потому что должен видеть список всех зарегестрированных комманд
        register(new ShowCommand(console, collectionManager));
        register(new InfoCommand(console,collectionManager));
        register(new SaveCommand(console, collectionManager));
        register(new ClearCommand(console, collectionManager));
        register(new ExitCommand(console));
        register(new RemoveFirstCommand(console, collectionManager));
        register(new RemoveHeadCommand(console, collectionManager));
        register(new SumOfNumberOfParticipantsCommand(console, collectionManager));
        register(new ExecuteScriptCommand(console, this));
        register(new AddIfMinCommand(console, collectionManager));
        register(new FilterLessThanBestAlbumCommand(console,collectionManager));
        register(new PrintDescendingCommand(console,collectionManager));

    }

    public void register(Command command) {
        commands.put(command.getName(), command);
    }
    //Записывает команды Map

    public Map<String, Command> getCommands() {

        return commands;
    }
    //Возвращает все доступные команды


}