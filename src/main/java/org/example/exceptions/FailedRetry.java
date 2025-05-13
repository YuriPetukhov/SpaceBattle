package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;

@Slf4j
public class FailedRetry implements Command {

    private final Command command;
    private final Exception e;

    public FailedRetry(Command command, Exception e) {
        this.command = command;
        this.e = e;
    }

    @Override
    public void execute() throws Exception {
        log.error("Command failed twice and will not be retried: {}", command.getClass().getSimpleName());
    }
}

