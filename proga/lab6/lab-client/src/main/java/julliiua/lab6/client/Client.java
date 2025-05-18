package julliiua.lab6.client;

import julliiua.lab6.client.managers.ConnectionManager;
import julliiua.lab6.client.managers.Ask;
import julliiua.lab6.client.utility.ElementValidate;
import julliiua.lab6.client.utility.StandardConsole;
import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.*;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.ArgumentValidator;
import julliiua.lab6.common.validators.NoArgValidator;
import julliiua.lab6.client.utility.Console;

import java.io.*;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public final class Client {
    private static final StandardConsole console;

    static {
        console = new StandardConsole();
    }

    private static final int SERVER_PORT = 15056;
    private static final String SERVER_HOST = "localhost";
    private static Map<String, Pair<ArgumentValidator, Boolean>> commandsData;
    private static final ConnectionManager networkManager = new ConnectionManager(SERVER_PORT, SERVER_HOST);
    private static int scriptStackCounter = 0;
    private static final int MAX_ATTEMPTS = 3;
    private static final int RETRY_DELAY_MS = 5000;

    public static void main(String[] args) {
        int attempts = 1;
        do {
            try {
                networkManager.connect();
                commandsData = networkManager.receive().getCommandsMap();
                commandsData.put("execute_script", new Pair<>(new NoArgValidator(), false));
                console.println("Connected to " + SERVER_HOST + ":" + SERVER_PORT);

                while (true) {
                    console.print("> ");
                    String inputCommand = console.readln();
                    ExecutionResponse argumentStatus = validateCommand((inputCommand.trim() + " ").split(" ", 2));

                    if (!argumentStatus.getExitCode()) {
                        console.printError(argumentStatus.getMessage());
                        continue;
                    }

                    Request request = prepareRequest(console, inputCommand);
                    if (request == null) continue;

                    networkManager.send(request);
                    Response response = networkManager.receive();

                    if (response.getExecutionStatus().getExitCode()) {
                        console.println(response.getExecutionStatus().getMessage());
                    } else {
                        console.printError(response.getExecutionStatus().getMessage());
                    }
                }
            } catch (BufferOverflowException | BufferUnderflowException | IOException e) {
                console.printError("Ошибка подключения. Попытка " + attempts + "/3");
                if (attempts < MAX_ATTEMPTS) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ignored) {}
                }
                attempts++;
            } catch (ClassNotFoundException e) {
                console.printError("Ошибка при обработке ответа сервера: " + e.getMessage());
                return;
            } catch (Ask.AskBreak e) {
                throw new RuntimeException(e);
            } catch (Ask.Breaker e) {
                throw new RuntimeException(e);
            }
        } while (attempts <= MAX_ATTEMPTS);
        console.printError("Превышено максимальное количество попыток подключения");
    }

    private static Request handleScript(String fileName) {
        ExecutionResponse scriptStatus = runScript(fileName);
        if (!scriptStatus.getExitCode()) {
            console.printError(scriptStatus.getMessage());
        }
        return null;
    }

    private static ExecutionResponse validateCommand(String[] userCommand) {
        try {
            if (userCommand[0].equals("exit")) {
                System.exit(1);
                return null;
            }

            if (userCommand[0].equals("execute_script")) {
                return new ExecutionResponse(true, "Script command recognized");
            }

            Pair<ArgumentValidator, Boolean> validator = commandsData.get(userCommand[0]);
            if (validator == null) {
                return new ExecutionResponse(false,
                        "Unknown command. Type 'help' for list");
            }

            return validator.getFirst().validate(userCommand[1].trim(), userCommand[0]);
        } catch (NullPointerException e) {
            return new ExecutionResponse(false,
                    "Not enough arguments for command");
        }
    }

    private static Request askingRequest(Console console, String inputCommand) throws Ask.AskBreak, Ask.Breaker {
        ElementValidate elementValidator = new ElementValidate();
        Pair<ExecutionResponse, MusicBand> validationStatusPair = elementValidator.validateAsking(console, Math.abs(new Random().nextLong()) + 1);
        if (!validationStatusPair.getFirst().getExitCode()) {
            console.printError(validationStatusPair.getFirst().getMessage());
            return null;
        } else {
            return new Request(inputCommand, validationStatusPair.getSecond());
        }
    }

    private static Request prepareRequest(Console console, String inputCommand) throws Ask.AskBreak, Ask.Breaker {
        String[] commands = (inputCommand.trim() + " ").split(" ", 2);
        if (commandsData.get(commands[0]).getSecond()) {
            return askingRequest(console, inputCommand); // Если команда требует построчного ввода
        } else if (commands[0].equals("execute_script")) {
            ExecutionResponse scriptStatus = runScript(commands[1].trim());
            if (!scriptStatus.getExitCode()) {
                console.printError(scriptStatus.getMessage());
                return null;
            }
            return null;
        } else {
            return new Request(inputCommand);
        }
    }

    private static ExecutionResponse runScript(String fileName) {
        scriptStackCounter++;
        try {
            if (scriptStackCounter > 100) {
                return new ExecutionResponse(false,
                        "Max script recursion depth (100) exceeded");
            }

            if (fileName.isEmpty()) {
                return new ExecutionResponse(false,
                        "Script filename required");
            }

            console.println("Executing script: " + fileName);
            File file = new File(fileName);

            if (!file.exists()) {
                return new ExecutionResponse(false,
                        "Script file not found");
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.equals("exit")) break;
                    if (line.isEmpty()) continue;

                    Request request = prepareRequest(console, line);
                    if (request == null) {
                        return new ExecutionResponse(false,
                                "Script execution stopped");
                    }

                    networkManager.send(request);
                    Response response = networkManager.receive();
                    ExecutionResponse status = response.getExecutionStatus();

                    if (status.getExitCode()) {
                        console.println(status.getMessage());
                    } else {
                        console.printError(status.getMessage());
                        return new ExecutionResponse(false,
                                "Script execution paused");
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Ask.AskBreak e) {
                throw new RuntimeException(e);
            } catch (Ask.Breaker e) {
                throw new RuntimeException(e);
            }
            return new ExecutionResponse(true, "Script completed");
        } finally {
            scriptStackCounter--;
        }
    }
}