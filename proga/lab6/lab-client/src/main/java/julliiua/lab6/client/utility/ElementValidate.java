package julliiua.lab6.client.utility;

import julliiua.lab6.client.managers.Ask;
import julliiua.lab6.client.managers.Ask.Breaker;
import julliiua.lab6.common.models.MusicBand;
import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.utility.Pair;

/**
 * Валидатор для проверки корректности элемента коллекции.
 */
public class ElementValidate {
    /**
     * Проверяет корректность введенного элемента коллекции.
     *
     * @param console Консоль для ввода/вывода.
     * @param id Идентификатор элемента коллекции.
     * @return Пара, содержащая статус выполнения проверки и элемент коллекции.
     */
    public Pair<ExecutionResponse, MusicBand> validateAsking(Console console, Long id) throws Ask.AskBreak, Breaker {
        try {
            MusicBand band = Ask.askMusicBand(console);
            return validating(band);
        } catch (Ask.IllegalInputException e) {
            return new Pair<>(new ExecutionResponse(false, e.getMessage()), null);
        }
    }

    public Pair<ExecutionResponse, MusicBand> validating(MusicBand band) {
        if (band != null && band.validate()) {
            return new Pair<>(new ExecutionResponse(true, "Элемент введён корректно!"), band);
        }
        return new Pair<>(new ExecutionResponse(false, "Введены некорректные данные!"), null);
    }
}
