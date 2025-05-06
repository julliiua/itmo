package julliiua.lab6.common.validators;

import julliiua.lab6.common.utility.ExecutionResponse;

/**
 * Абстрактный класс для валидаторов аргументов команд.
 */

public abstract class ArgumentValidator {
    /**
     * Проверяет аргумент команды.
     *
     * @param arg Аргумент команды.
     * @param name Имя команды.
     * @return Статус выполнения проверки.
     */
    public abstract ExecutionResponse validate(String arg, String name);
}