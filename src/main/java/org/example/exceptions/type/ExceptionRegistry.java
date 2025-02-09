package org.example.exceptions.type;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.exceptions.ExceptionHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ExceptionRegistry {

    private final Map<Class<? extends Exception>, ExceptionHandling> exceptionActionMap =
            new HashMap<>();
    @Autowired
    public ExceptionRegistry(List<ExceptionHandling> exceptionHandlings) {
        for (ExceptionHandling exceptionHandling : exceptionHandlings) {
            exceptionActionMap.put(exceptionHandling.getExceptionType(), exceptionHandling);
        }
    }

    public void handleException(Command command, Exception e) throws Exception {
        ExceptionHandling exceptionHandling = exceptionActionMap.getOrDefault(
                e.getClass(), new GeneralException(command, e));
        exceptionHandling.execute();
    }
}
