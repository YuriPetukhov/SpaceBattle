package org.example.exceptions.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.FailedRetryCommand;
import org.example.exceptions.handler.LogExceptionHandler;
import org.example.exceptions.handler.RetryExceptionHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlingStrategy {

    private static final int MAX_RETRIES = 2;

    private final CommandQueue commandQueue;
    private final RetryExceptionHandler retryExceptionHandler;
    private final LogExceptionHandler logExceptionHandler;

    private int retryCount = 0;

    public void handleException(Command command, Exception exception) throws Exception {
        if (retryCount < MAX_RETRIES) {
            log.info("Attempt {}: Retrying command: {}", retryCount + 1, command.getClass().getSimpleName());
            retryExceptionHandler.handleException(command, exception);
            retryCount++;
        } else {
            log.error("Retry failed for command: {} after {} attempts. Logging exception.", command.getClass().getSimpleName(), MAX_RETRIES);
            FailedRetryCommand failedRetryCommand = new FailedRetryCommand(command, exception);
            commandQueue.add(failedRetryCommand);
            logExceptionHandler.handleException(command, exception);
            retryCount = 0;
        }
    }
}

