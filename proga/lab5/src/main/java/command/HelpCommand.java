package command;

import manager.CommandManager;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'help'. Выводит список доступных команд.
 */
public class HelpCommand extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public HelpCommand(Console console, CommandManager commandManager) {
        super("help", "вывести список доступных команд");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Команда 'help' не принимает аргументы!");
        }
        console.println("Доступные команды:");
        for (Command command : commandManager.getCommands().values()) {
            console.println("  " + command.getName() + " - " + command.getDescription());
        }
        return new ExecutionResponse(true,"Список команд выведен.");
    }
}
