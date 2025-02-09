package org.example.exceptions.type;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.exceptions.AbstractExceptionHandling;
import org.example.exceptions.ExceptionHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ExceptionRegistry {

    private final Map<String, AbstractExceptionHandling> exceptionActionMap = new HashMap<>();

    @Autowired
    public ExceptionRegistry(List<AbstractExceptionHandling> exceptionHandlings) {
        for (AbstractExceptionHandling exceptionHandling : exceptionHandlings) {
            exceptionActionMap.put(exceptionHandling.getClassName(), exceptionHandling);
        }
    }

    public void handleException(String classname, String message) {
        AbstractExceptionHandling exceptionHandling = exceptionActionMap.getOrDefault(
                classname, new GeneralException(message, classname));
        exceptionHandling.execute();
    }
}
