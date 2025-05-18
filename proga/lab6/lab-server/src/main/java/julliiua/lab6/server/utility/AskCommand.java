package julliiua.lab6.server.utility;

import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.validators.ArgumentValidator;
import julliiua.lab6.common.validators.IdValidator;
import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.server.commands.Command;

/**
 * Абстрактный класс для команд, требующих ввода данных.
 *
 * @param <T> Тип валидатора аргументов.
 */
public abstract class AskCommand<T extends ArgumentValidator> extends Command<T> {

    /**
     * Конструктор команды AskingCommand.
     *
     * @param name              Имя команды.
     * @param description       Описание команды.
     * @param argumentValidator Валидатор аргументов команды.
     */
    public AskCommand(String name, String description, T argumentValidator) {
        super(name, description, 0, argumentValidator);
    }


    /**
     * Выполняет команду с аргументом.
     *
     * @param arg Аргумент команды.
     * @return Статус выполнения команды.
     */
    @Override
    protected ExecutionResponse runInternal(String arg) {
        return null;
    }

    /**
     * Выполняет команду с элементом коллекции.
     *
     * @param band Элемент коллекции.
     * @return Статус выполнения команды.
     */
    protected abstract ExecutionResponse runInternal(MusicBand band);

    /**
     * Запускает выполнение команды.
     *
     * @param arg Аргумент команды.
     * @return Статус выполнения команды.
     */
    public ExecutionResponse execute(String arg, MusicBand band) {
        ExecutionResponse argumentStatus = argument.validate(arg, getName());
        if (argumentStatus.getExitCode()) {
            int id;
            if (argument instanceof IdValidator) {
                id = (int) Long.parseLong(arg);
                if (collectionManager.getById(id) == null) {
                    return new ExecutionResponse(false, "Элемент с указанным id не найден!");
                }
            } else {
                id = collectionManager.getFreeId();
            }
            return runInternal(band);
        } else {
            return argumentStatus;
        }
    }

    @Override
    public ExecutionResponse execute(String arg) {
        return new ExecutionResponse(false, "Метод должен вызываться с аргументом MusicBand!");
    }
}