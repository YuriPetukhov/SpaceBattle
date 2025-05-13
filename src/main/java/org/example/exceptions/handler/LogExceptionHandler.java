package org.example.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.LogException;

@Slf4j
public class LogExceptionHandler implements CommandHandler {

    private final CommandQueue commandQueue;

    public LogExceptionHandler(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void handle(Command command, Exception e) throws Exception {
        LogException logCommand = new LogException(command, e);
        commandQueue.add(logCommand);
    }
}