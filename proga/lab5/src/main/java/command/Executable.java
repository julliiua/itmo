package command;

import utility.Ask;
import utility.ExecutionResponse;

/**
 * Интерфейс для всех выполняемых команд.
 */
public interface Executable {
    ExecutionResponse apply(String arguments) throws Ask.AskBreak;
}
