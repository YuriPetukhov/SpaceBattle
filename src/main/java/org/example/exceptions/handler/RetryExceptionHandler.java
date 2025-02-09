package org.example.exceptions.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.Retry;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryExceptionHandler {

    private final CommandQueue commandQueue;

    public void handleException(Command command, Exception exception) throws Exception {
        Retry retryCommand = new Retry(command, exception);
        commandQueue.add(retryCommand);
    }
}
