package julliiua.lab6.common.utility;

import java.io.Serial;
import java.io.Serial;
import java.io.Serializable;

/**
 * Класс, представляющий ответ команды.
 */
public class ExecutionResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 13L;
    private final boolean exitCode;
    private final String message;

    public ExecutionResponse(boolean code, String message) {
        this.exitCode = code;
        this.message = message;
    }

    public boolean getExitCode() {
        return exitCode;
    }

    public String getMessage() {
        return message;
    }

}