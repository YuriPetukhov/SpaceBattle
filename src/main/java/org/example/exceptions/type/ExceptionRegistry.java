package org.example.exceptions.type;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.exceptions.ExceptionHandlingCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ExceptionRegistry {

    private final Map<Class<? extends Exception>, ExceptionHandlingCommand> exceptionActionMap =
            new HashMap<>();
    @Autowired
    public ExceptionRegistry(List<ExceptionHandlingCommand> exceptionHandlingCommands) {
        for (ExceptionHandlingCommand exceptionHandlingCommand : exceptionHandlingCommands) {
            exceptionActionMap.put(exceptionHandlingCommand.getExceptionType(), exceptionHandlingCommand);
        }
    }

    public void handleException(Command command, Exception e) throws Exception {
        ExceptionHandlingCommand exceptionHandlingCommand = exceptionActionMap.getOrDefault(
                e.getClass(), new GeneralExceptionHandlingCommand(command, e));
        exceptionHandlingCommand.execute();
    }
}
