package command;

import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'exit'. Завершает выполнение программы без сохранения.
 */
public class ExitCommand extends Command {
    private final Console console;

    public ExitCommand(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }

    @Override
    public ExecutionResponse apply(String argument) {
        if (!argument.isEmpty()) {
            return new ExecutionResponse(false, "Ошибка: Команда 'exit' не принимает аргументы.");
        }

        console.println("Завершение работы программы...");
        System.exit(0);
        return null;
    }
}
