package org.example.exceptions.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.*;
import org.example.exceptions.type.ExceptionRegistry;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class ExceptionHandler implements Handler {

    private final ExceptionRegistry exceptionRegistry;

    public void handle(String classname, Exception e) {
        exceptionRegistry.handleException(classname, e.getMessage());
    }
}
