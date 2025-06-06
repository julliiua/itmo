package start;

import manager.*;
import utility.*;
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Класс Runner управляет режимами работы программы.
 */
public class Runner {
    private final Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();

    /**
     * Конструктор Runner
     */
    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Интерактивный режим работы
     */
    public void interactiveMode() {
        try {
            ExecutionResponse commandResponse;
            String[] userCommand = {"", ""};
            boolean exitComm = true;

            while (exitComm) {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandResponse = launchCommand(userCommand);

                console.println(commandResponse.getMessage());
                if (commandResponse.getMessage().equals("exit"))
                    exitComm = false;
            }
        } catch (NoSuchElementException e) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException e) {
            console.printError("Непредвиденная ошибка!");
        }
    }

    /**
     * Проверяет рекурсию при выполнении скриптов
     */
    private boolean checkRecursion(String scriptFile) {
        for (String script : scriptStack) {
            if (scriptFile.equals(script)) {
                console.println("Рекурсия выполняется больше 1 раза. Прерываем выполнение.");
                return false;
            }
        }
        return true;
    }


    /**
     * Выполняет команды из скрипта
     * @param scriptFile Имя файла скрипта
     * @return Результат выполнения команд
     */
    private ExecutionResponse scriptMode(String scriptFile) {
        String[] userCommand = {"", ""};
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(scriptFile).exists()) return new ExecutionResponse(false, "Файл не существует!");
        if (!Files.isReadable(Paths.get(scriptFile))) return new ExecutionResponse(false, "Нет прав на чтение файла!");

        scriptStack.add(scriptFile);
        try (Scanner scriptScanner = new Scanner(new File(scriptFile))) {
            ExecutionResponse commandResponse;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);

            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                while (console.isCanReadln() && userCommand[0].isEmpty()) {
                    userCommand = (console.readln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }

                boolean shouldExecute = true;
                if (userCommand[0].equals("execute_script")) {
                    shouldExecute = checkRecursion(userCommand[1]);
                }
                if (shouldExecute){
                    commandResponse = launchCommand(userCommand);
                } else {
                    commandResponse = new ExecutionResponse(false,"Обнаружена рекурсия");
                }

                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scriptScanner);
                executionOutput.append(commandResponse.getMessage()).append("\n");

            } while (commandResponse.getExitCode() && !commandResponse.getMessage().equals("exit") && console.isCanReadln());

            console.selectConsoleScanner();
            if (!commandResponse.getExitCode() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionOutput.append("Ошибка выполнения скрипта!\n");
            }

            return new ExecutionResponse(commandResponse.getExitCode(), executionOutput.toString());
        } catch (FileNotFoundException e) {
            return new ExecutionResponse(false, "Файл скрипта не найден!");
        } catch (NoSuchElementException e) {
            return new ExecutionResponse(false, "Файл пуст!");
        } catch (IllegalStateException e) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return new ExecutionResponse(true, "");
    }

    /**
     * Выполняет команду.
     * @param userCommand Команда и аргументы
     * @return Результат выполнения команды
     */
    private ExecutionResponse launchCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse(true, "");

        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) {
            return new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки.");
        }

        switch (userCommand[0]) {
            case "execute_script" -> {
                ExecutionResponse checkScript = commandManager.getCommands().get("execute_script").apply(userCommand[1]);
                if (!checkScript.getExitCode()) return checkScript;
                ExecutionResponse scriptExecution = scriptMode(userCommand[1]);
                return new ExecutionResponse(scriptExecution.getExitCode(), checkScript.getMessage() + "\n" + scriptExecution.getMessage().trim());
            }
            default -> {
                return command.apply(userCommand[1]);
            }
        }
    }
}