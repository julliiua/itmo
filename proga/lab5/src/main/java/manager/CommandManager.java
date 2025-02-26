package manager;

import command.*;
import utility.*;

import java.util.*;

/**
 * Менеджер команд: отвечает за регистрацию и выполнение команд.
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final List<String> commandHistory = new ArrayList<>();

    /**
     * Конструктор CommandManager.
     * Регистрирует все доступные команды.
     */
    public CommandManager(Console console, CollectionManager collectionManager) {
        register(new AddCommand(console, collectionManager));
        register(new UpdateCommand(console, collectionManager));
        register(new RemoveCommand(console, collectionManager));
        register(new HelpCommand(console, this));
        register(new ShowCommand(console, collectionManager));
        register(new InfoCommand(console,collectionManager));
        register(new SaveCommand(console, collectionManager));
    }

    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    public ExecutionResponse executeCommand(String commandName, String argument) throws Ask.AskBreak {
        Command command = commands.get(commandName);

        if (command == null) {
            return new ExecutionResponse(false, "Ошибка: команда '" + commandName + "' не найдена.");
        }

        return command.apply(argument);
    }

    /**
     * Обрабатывает ввод пользователя (разделяет команду и аргумент)
     */
    public ExecutionResponse execute(String input) throws Ask.AskBreak {
        String[] parts = input.trim().split(" ", 2);
        String commandName = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";

        return executeCommand(commandName, argument);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void addToHistory(String commandName) {
        if (commandHistory.size() >= 10) {
            commandHistory.remove(0);
        }
        commandHistory.add(commandName);
    }

    public List<String> getHistory() {
        return new ArrayList<>(commandHistory);

    }

}