package org.example.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class FailedRetry implements Command {

    private final Command command;
    private final Exception e;

    @Override
    public void execute() throws Exception {
        log.error("Command failed twice and will not be retried: {}", command.getClass().getSimpleName());
    }
}

