package org.example.exceptions.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.LogExceptionCommand;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogExceptionHandler {

    private final CommandQueue commandQueue;

    public void handleException(Command command, Exception exception) throws Exception {
        LogExceptionCommand logCommand = new LogExceptionCommand(command, exception);
        commandQueue.add(logCommand);
    }
}
