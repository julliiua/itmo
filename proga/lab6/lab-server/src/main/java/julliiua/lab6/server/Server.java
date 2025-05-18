package julliiua.lab6.server;


import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.utility.Pair;
import julliiua.lab6.common.utility.Request;
import julliiua.lab6.common.utility.Response;
import julliiua.lab6.common.validators.ArgumentValidator;
import julliiua.lab6.server.commands.*;
import julliiua.lab6.server.managers.*;
import julliiua.lab6.server.utility.AskCommand;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.*;

public final class Server {
    String fileName = System.getenv("FILENAME");
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    static { initLogger();}
    private static void initLogger() {
        try {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String color = switch (record.getLevel().getName()) {
                        case "SEVERE" -> "\u001B[31m"; // Красный
                        case "WARNING" -> "\u001B[33m"; // Желтый
                        case "INFO" -> "\u001B[32m"; // Зеленый
                        default -> "\u001B[0m"; // Сброс цвета
                    };
                    return color + "[" + record.getLevel() + "] " +
                            "[" + Thread.currentThread().getName() + "] " +
                            "[" + new java.util.Date(record.getMillis()) + "] " +
                            formatMessage(record) + "\u001B[0m\n";
                }
            });
            // Настройка FileHandler
            FileHandler fileHandler = new FileHandler("server_logs.log", true); // true для добавления логов в конец файла
            fileHandler.setFormatter(new SimpleFormatter()); // Устанавливаем простой форматтер

            // Добавление обработчиков в логгер
            Server.logger.setUseParentHandlers(false);
            Server.logger.addHandler(consoleHandler);
            Server.logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Failed to initialize file handler for logger: " + e.getMessage());
        }
    }

    private static final int PORT = 15056;
    private static CommandManager commandManager;
    private static ConnectionManager networkManager;
    private static Selector selector;
    private static Response response;
    private static final CollectionManager collectionManager = CollectionManager.getInstance();
    private static volatile boolean isRunning = true;

    public static void main(String[] args) {
        ExecutionResponse loadStatus = collectionManager.load();


        networkManager = new ConnectionManager(PORT);

        // Проверка успешности загрузки коллекции
        if (!loadStatus.getExitCode()) {
            logger.severe(loadStatus.getMessage().toString());
            System.exit(1);
        }
        logger.info("The collection file has been successfully loaded!");

        // Регистрация команд
        commandManager = new CommandManager() {{
            register("help", new Help(this));
            register("add", new Add(collectionManager));
            register("show", new Show(collectionManager));
            register("info", new Info(collectionManager));
            register("save",new Save(collectionManager));
        }};
        Runner runner = new Runner(commandManager);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                isRunning = false; // Останавливаем цикл
                collectionManager.saveCollection();
                selector.close();
                networkManager.close();
            } catch (Exception e) {
                logger.severe("An error occurred while shutting down the server: " + e.getMessage());
            }
        }));
        run(runner);
    }


    public static void run(Runner runner) {
        try {
            selector = Selector.open();
            networkManager.startServer();
            networkManager.getServerSocketChannel().register(selector, SelectionKey.OP_ACCEPT);

            logger.info("Selector started");
            logger.info("To stop the server, press [Ctrl + C]");

            while (isRunning) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    logger.info("Processing key: " + key);

                    try {
                        if (key.isValid()) {
                            // Принимаем новое соединение
                            if (key.isAcceptable()) {
                                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                                SocketChannel clientChannel = serverSocketChannel.accept();
                                logger.info("Client connected: " + clientChannel.getRemoteAddress());
                                // Настройка канала для неблокирующего режима
                                clientChannel.configureBlocking(false);
                                InitialCommandsData(clientChannel, key); // Отправка клиенту списка команд
                            }
                            else if (key.isReadable()) {
                                SocketChannel clientChannel = (SocketChannel) key.channel();
                                clientChannel.configureBlocking(false);

                                Request request;
                                try {
                                    request = networkManager.receive(clientChannel, key);
                                } catch (ConnectionManager.NullRequestException | SocketException | NullPointerException e) {
                                    logger.severe("Error receiving request from client: " + e.getMessage());
                                    collectionManager.saveCollection();
                                    logger.info("Collection saved successfully");
                                    key.cancel();
                                    continue;
                                }
                                logger.info("Request received from client: " + request);
                                ExecutionResponse executionStatus = runner.launchCommand(request.getCommand(), request.getBand());
                                response = new Response(executionStatus);
                                if (!executionStatus.getExitCode()) {
                                    logger.severe(executionStatus.getMessage().toString());
                                } else {
                                    logger.info("Command executed successfully");
                                }
                                clientChannel.register(selector, SelectionKey.OP_WRITE);

                            } else if (key.isWritable()) {
                                SocketChannel clientChannel = (SocketChannel) key.channel();
                                clientChannel.configureBlocking(false);

                                try {
                                    networkManager.send( response,clientChannel);
                                    logger.info("Response sent to client: " + clientChannel.getRemoteAddress());
                                    clientChannel.register(selector, SelectionKey.OP_READ);
                                } catch (IOException e) {
                                    logger.severe("Error sending response to client: " + e.getMessage());
                                    collectionManager.saveCollection();
                                    logger.info("Collection saved successfully");
                                    key.cancel();
                                }
                            }
                        }
                    } catch (SocketException | CancelledKeyException e) {
                        try (var channel = key.channel()) {
                            logger.severe("Client " + channel.toString() + " disconnected");
                            collectionManager.saveCollection();
                            logger.info("Collection saved successfully");
                            key.cancel();
                        }
                    } finally {
                        keys.remove();
                    }
                }
            }
        } catch (ClosedSelectorException e) {
            logger.warning("Selector was closed.");
        } catch (EOFException e) {
            collectionManager.saveCollection();
            logger.info("Collection saved successfully");
            logger.severe(e.getMessage());
            System.exit(1);
        } catch (IOException | NullPointerException | ClassNotFoundException e) {
            logger.severe("Error while running the server: " + e.getMessage());
        }
    }


    private static void InitialCommandsData(SocketChannel clientChannel, SelectionKey key) throws ClosedChannelException {
        try {
            Map<String, Pair<ArgumentValidator, Boolean>> commandsData = new HashMap<>();
            for (var entrySet : commandManager.getCommands().entrySet()) {
                boolean isAskingCommand = AskCommand.class.isAssignableFrom(entrySet.getValue().getClass());
                commandsData.put(entrySet.getKey(), new Pair<>(entrySet.getValue().getArgument(), isAskingCommand));
            }
            networkManager.send(new Response(commandsData), clientChannel);
            logger.info("Command list sent to the client: " + clientChannel.getRemoteAddress());
        } catch (IOException e) {
            logger.severe("Error sending command list to the client: " + e.getMessage());
            key.cancel();
        } catch (NullPointerException e) {
            logger.severe("The client disconnected from the server: " + e.getMessage());
            key.cancel();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clientChannel.register(selector, SelectionKey.OP_READ);
    }
}