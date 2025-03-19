package command;

import manager.CommandManager;
import utility.Console;
import utility.ExecutionResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Команда 'execute_script'. Выполняет команды из указанного файла.
 */
public class ExecuteScriptCommand extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public ExecuteScriptCommand(Console console, CommandManager commandManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (argument.isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Укажите имя файла.");
        }

        File scriptFile = new File(argument);
        if (!scriptFile.exists()) {
            return new ExecutionResponse(false, "Ошибка: Файл '" + argument + "' не найден.");
        }
        if (!Files.isReadable(Paths.get(argument))) {
            return new ExecutionResponse(false, "Ошибка: Нет прав на чтение файла '" + argument + "'.");
        }

        try (Scanner scriptScanner = new Scanner(scriptFile)) {
            console.selectFileScanner(scriptScanner);
            return new ExecutionResponse(true, "Скрипт выполнен успешно.");
        } catch (FileNotFoundException e) {
            return new ExecutionResponse(false, "Ошибка: Файл '" + argument + "' не найден.");
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка выполнения скрипта: " + e.getMessage());
        }
    }

    private static class UnknownCommand extends Command {
        public UnknownCommand(String name) {
            super(name, "неизвестная команда");
        }

        @Override
        public ExecutionResponse apply(String argument) {
            return new ExecutionResponse(false, "Ошибка: Команда '" + getName() + "' не найдена.");
        }
    }
}
