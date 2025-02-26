package add;

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
    private int recursionDepth = -1;

    /**
     * Конструктор Runner.
     * @param console         Объект для работы с вводом/выводом
     * @param commandManager  Менеджер команд
     */
    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Интерактивный режим работы.
     */
    public void interactiveMode() {
        try {
            ExecutionResponse commandResponse;
            String[] userCommand = {"", ""};

            while (true) {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandResponse = launchCommand(userCommand);

                if (commandResponse.getMessage().equals("exit")) break;
                console.println(commandResponse.getMessage());
            }
        } catch (NoSuchElementException e) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException e) {
            console.printError("Непредвиденная ошибка!");
        }
    }

    /**
     * Проверяет рекурсию при выполнении скриптов.
     * @param scriptFile Имя скрипта
     * @param scriptScanner Scanner для работы с файлом
     * @return Можно ли продолжить выполнение скрипта
     */
    private boolean checkRecursion(String scriptFile, Scanner scriptScanner) {
        int recursionStart = -1;
        int i = 0;
        for (String script : scriptStack) {
            i++;
            if (scriptFile.equals(script)) {
                if (recursionStart < 0) recursionStart = i;
                if (recursionDepth < 0) {
                    console.selectConsoleScanner();
                    console.println("Обнаружена рекурсия! Введите максимальную глубину (0..500)");
                    while (recursionDepth < 0 || recursionDepth > 500) {
                        try {
                            console.print("> ");
                            recursionDepth = Integer.parseInt(console.readln().trim());
                        } catch (NumberFormatException e) {
                            console.println("Некорректный ввод, попробуйте еще раз.");
                        }
                    }
                    console.selectFileScanner(scriptScanner);
                }
                if (i > recursionStart + recursionDepth || i > 500) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Выполняет команды из скрипта.
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

                executionOutput.append(console.getPrompt()).append(String.join(" ", userCommand)).append("\n");

                boolean shouldExecute = true;
                if (userCommand[0].equals("execute_script")) {
                    shouldExecute = checkRecursion(userCommand[1], scriptScanner);
                }

                commandResponse = shouldExecute ? launchCommand(userCommand) : new ExecutionResponse(false, "Превышена глубина рекурсии");

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