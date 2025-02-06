package org.example.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;

@Slf4j
@Getter
@RequiredArgsConstructor
public abstract class AbstractExceptionHandlingCommand implements ExceptionHandlingCommand {

    protected final Command command;
    private final Exception exception;

    @Override
    public void execute() {
        logException();
        handleSpecificLogic();
    }

    protected void logException() {
        log.error("Exception occurred for command '{}': {}",
                command.getClass().getSimpleName(), exception.getMessage());
    }
    protected abstract void handleSpecificLogic();

    @Override
    public Class<? extends Exception> getExceptionType() {
        return exception.getClass();
    }
}
