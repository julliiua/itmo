package julliiua.lab6.server.managers;

import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.ArgumentValidator;
import julliiua.lab6.server.Server;
import julliiua.lab6.server.commands.Command;
import julliiua.lab6.server.utility.AskCommand;

public class Runner {
    private final CommandManager commandManager;

    /**
     * Конструктор Runner
     */
    public Runner(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    private ExecutionResponse validateCommand(String[] userCommand) {
        try {
            Command<?> command = commandManager.getCommands().get(userCommand[0]);
            if (command == null) {
                return new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки.");
            } else {
                ArgumentValidator argumentValidator = command.getArgument();
                return argumentValidator.validate(userCommand[1].trim(), command.getName());
            }
        } catch (NullPointerException e) {
            return new ExecutionResponse(false, "Введено недостаточно аргументов для выполнения последней команды!");
        }
    }

    /**
     * Запускает команду.
     * @param userCommand команда для запуска
     * @return код завершения
     */
    public ExecutionResponse launchCommand(String[] userCommand, MusicBand musicBand) {
        ExecutionResponse validateStatus = validateCommand(userCommand);
        if (validateStatus.getExitCode()) {
            Command<?> command = commandManager.getCommands().get(userCommand[0]);
            Server.logger.info("Выполнение команды '" + userCommand[0] + "'");
            if (AskCommand.class.isAssignableFrom(command.getClass())) {
                return ((AskCommand<?>) command).execute(userCommand[1], musicBand);
            } else {
                return command.execute(userCommand[1]);
            }
        } else {
            return new ExecutionResponse(false, validateStatus.getMessage());
        }
    }
}

