package julliiua.lab6.common.validators;

import julliiua.lab6.common.utility.ExecutionResponse;

import java.io.Serial;
import java.io.Serializable;
/**
 * Валидатор для проверки отсутствия аргументов у команды.
 */
public class NoArgValidator extends ArgumentValidator implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Проверяет корректность аргумента команды.
     *
     * @param arg  Аргумент команды.
     * @param name Имя команды.
     * @return Статус выполнения проверки.
     */
    @Override
    public ExecutionResponse validate(String arg, String name) {
        if (!arg.isEmpty()) {
            return new ExecutionResponse(false,"У команды нет аргументов!\nПример корректного ввода: " + name);
        }
        return new ExecutionResponse(true, "Аргумент команды введен корректно.");
    }
}