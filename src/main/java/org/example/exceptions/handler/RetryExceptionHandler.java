package org.example.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.Retry;

@Slf4j
public class RetryExceptionHandler implements CommandHandler {

    private final CommandQueue commandQueue;

    public RetryExceptionHandler(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void handle(Command command, Exception e) throws Exception {
        Retry retryCommand = new Retry(command, e);
        commandQueue.add(retryCommand);
    }
}
