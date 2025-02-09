package org.example.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public abstract class AbstractExceptionHandling extends Exception {

    private final String className;
    protected final String message;

    public void execute() {
        logException();
        handleSpecificLogic();
    }

    protected void logException() {
        log.error("Exception occurred for class '{}': {}",
                className, message);
    }
    protected abstract void handleSpecificLogic();

}
