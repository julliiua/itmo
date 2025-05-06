package julliiua.lab6.server.commands;

import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.server.managers.CommandManager;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.server.commands.Command;

/**
 * Команда 'help'. Выводит справку по доступным командам
 **/
public class Help extends Command<NoArgValidator>  {
    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам", 0, new NoArgValidator());
        this.commandManager = commandManager;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse runInternal(String argument) {
        for (Command command : commandManager.getCommands().values()) {
            new ExecutionResponse(true,"  " + command.getName() + " - " + command.getDescription());
        }
        return new ExecutionResponse(true,"Список команд выведен.");
    }

    @Override
    public ExecutionResponse innerExecute(String arg) {
        return null;
    }
}