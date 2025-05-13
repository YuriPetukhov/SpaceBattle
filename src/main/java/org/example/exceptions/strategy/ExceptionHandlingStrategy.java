package org.example.exceptions.strategy;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.FailedRetry;
import org.example.exceptions.handler.CommandHandler;
import org.example.exceptions.handler.LogExceptionHandler;
import org.example.exceptions.handler.RetryExceptionHandler;

@Slf4j
public class ExceptionHandlingStrategy implements CommandHandler {

    private static final int MAX_RETRIES = 2;

    private final CommandQueue commandQueue;
    private final RetryExceptionHandler retryExceptionHandler;
    private final LogExceptionHandler logExceptionHandler;

    private int retryCount = 0;

    public ExceptionHandlingStrategy(CommandQueue commandQueue, RetryExceptionHandler retryExceptionHandler, LogExceptionHandler logExceptionHandler) {
        this.commandQueue = commandQueue;
        this.retryExceptionHandler = retryExceptionHandler;
        this.logExceptionHandler = logExceptionHandler;
    }

    public void handle(Command command, Exception e) throws Exception {
        if (retryCount < MAX_RETRIES) {
            log.info("Attempt {}: Retrying command: {}", retryCount + 1, command.getClass().getSimpleName());
            retryExceptionHandler.handle(command, e);
            retryCount++;
        } else {
            log.error("Retry failed for command: {} after {} attempts. Logging exception.", command.getClass().getSimpleName(), MAX_RETRIES);
            FailedRetry failedRetryCommand = new FailedRetry(command, e);
            commandQueue.add(failedRetryCommand);
            logExceptionHandler.handle(command, e);
            retryCount = 0;
        }
    }
}

