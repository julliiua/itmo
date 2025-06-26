package julliiua.lab6.client.managers;

import julliiua.lab6.client.utility.Console;
import julliiua.lab6.common.utility.Request;
import julliiua.lab6.common.utility.Response;
import julliiua.lab6.common.utility.User;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationManager {
    private static String getHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update(password.getBytes());
            byte[] hashedPassword = md.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedPassword) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Ошибка: алгоритм шифрования SHA-384 не поддерживается.");
            throw new RuntimeException(e);
        }
    }

    public static User sendAuthenticationRequest(ConnectionManager connectionManager, Console console, User user, String inputCommand) throws IOException, ClassNotFoundException {
        Request request = new Request(inputCommand, user);
        connectionManager.send(request);
        Response authResponse = connectionManager.receive();
        if (authResponse.getExecutionStatus() == null) {
            console.printError("Ошибка: сервер не ответил на запрос.");
            return null;
        }
        if (authResponse.getExecutionStatus().getExitCode()) {
            console.println(authResponse.getExecutionStatus().getMessage());
            return user;
        } else {
            console.printError(authResponse.getExecutionStatus().getMessage());
            return null;
        }
    }

    public static User authenticateUser(ConnectionManager networkManager, Console console) throws IOException, ClassNotFoundException {
        while (true) {
            console.println("Введите команду 'register' для регистрации или 'login' для авторизации:");
            String inputCommand = console.readln().trim().toLowerCase();
            if (inputCommand.equals("register") || inputCommand.equals("login")) {
                console.println("Введите логин:");
                String username = console.readln();
                console.println("Введите пароль для авторизации:");
                String password = getHash(console.readln());
                User user = sendAuthenticationRequest(networkManager, console, new User(username, password), inputCommand);

                if (user != null) {
                    return user;
                }
            } else {
                console.printError("Команда '" + inputCommand + "' не найдена!");
            }
        }
    }



}