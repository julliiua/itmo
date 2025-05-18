package julliiua.lab6.server.commands;

import julliiua.lab6.common.utility.ExecutionResponse;
import julliiua.lab6.common.utility.Pair;
import julliiua.lab6.common.utility.ValidAnswer;
import julliiua.lab6.common.validators.ArgumentValidator;
import julliiua.lab6.server.managers.CollectionManager;

/**
 * Абстрактный класс для всех команд.
 */
public abstract class Command<T extends ArgumentValidator> {
    private final Pair<String, String> name;
    private final int i;
    protected static final CollectionManager collectionManager = CollectionManager.getInstance();
    public final T argument;

    public Command(String name, String description, int i, T argumentValidator) {
        this.name = new Pair<>(name, description);
        this.i = i;
        this.argument = argumentValidator;
    }

    public String getName() {
        return name.getFirst();
    }

    public String getDescription() {
        return name.getSecond();
    }
    public T getArgument() {
        return argument;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.getFirst().equals(command.name.getFirst()) && name.getSecond().equals(command.name.getSecond());
    }

    @Override
    public int hashCode() {
        return name.getFirst().hashCode() + name.getSecond().hashCode();
    }

    @Override
    public String toString() {
        return "Command{name='" + name.getFirst() + "', description='" + name.getSecond() + "'}";
    }

    protected abstract ExecutionResponse runInternal(String arg);

    public ExecutionResponse validate(String[] args) {
        if (i == 0 && !args[1].isEmpty()) return new ExecutionResponse(false, "Incorrect number of arguments!\nCorrect: '" + getName() + "'");
        if (i == 1 && args[1].isEmpty()) return new ExecutionResponse(false, "Incorrect number of arguments!\nCorrect: '" + getName() + "'");
        return new ExecutionResponse(true, "");
    }

    public ExecutionResponse execute(String arg) {
        ExecutionResponse argumentStatus = argument.validate(arg, getName());
        if (argumentStatus.getExitCode()) {
            return runInternal(arg);
        } else {
            return argumentStatus;
        }
    }
}

