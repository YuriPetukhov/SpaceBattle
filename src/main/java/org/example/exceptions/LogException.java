package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;

@Slf4j
public class LogException implements Command {

    private final Command command;
    private final Exception e;

    public LogException(Command command, Exception e) {
        this.command = command;
        this.e = e;
    }

    @Override
    public void execute() throws Exception {
        log.error("Exception occurred during execution of command {}: {}", command.getClass().getSimpleName(), e.getMessage());
    }

}
