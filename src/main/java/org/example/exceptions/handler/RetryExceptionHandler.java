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
public class RetryExceptionHandler implements Handler {

    private final CommandQueue commandQueue;

    public void handle(Command command, Exception e) throws Exception {
        Retry retryCommand = new Retry(command, e);
        commandQueue.add(retryCommand);
    }
}
