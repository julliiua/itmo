package utility;

/**
 * Класс, представляющий ответ команды.
 */
public class ExecutionResponse {
    private boolean exitCode;
    private String message;

    public ExecutionResponse(boolean code, String message) {
        this.exitCode = code;
        this.message = message;
    }

    public ExecutionResponse(String message) {
        this(true, message);
    }

    public boolean getExitCode() {
        return exitCode;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return exitCode + ";" + message;
    }
}
