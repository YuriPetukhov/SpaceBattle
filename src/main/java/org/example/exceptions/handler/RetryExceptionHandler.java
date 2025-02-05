package org.example.exceptions.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.RetryCommand;

@Slf4j
@RequiredArgsConstructor
public class RetryExceptionHandler {

    private final CommandQueue commandQueue;

    public void handleException(Command command, Exception exception) throws Exception {
        RetryCommand retryCommand = new RetryCommand(command, exception);
        commandQueue.add(retryCommand);
    }
}
