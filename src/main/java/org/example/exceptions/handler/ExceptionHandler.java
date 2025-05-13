package org.example.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.type.ExceptionRegistry;

@Slf4j
public class ExceptionHandler implements Handler {

    private final ExceptionRegistry exceptionRegistry;

    public ExceptionHandler(ExceptionRegistry exceptionRegistry) {
        this.exceptionRegistry = exceptionRegistry;
    }

    public void handle(String classname, Exception e) {
        exceptionRegistry.handleException(classname, e.getMessage());
    }
}
