package org.example.threads;

import org.example.command.Command;

public class HandleExceptionCommand implements Command {
    private final Exception exception;

    public HandleExceptionCommand(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void execute() {
        System.err.println("Ошибка при выполнении команды: " + exception.getMessage());
    }
}
