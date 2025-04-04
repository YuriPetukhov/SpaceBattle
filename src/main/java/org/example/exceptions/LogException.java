package org.example.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class LogException implements Command {

    private final Command command;
    private final Exception e;

    @Override
    public void execute() throws Exception {
        log.error("Exception occurred during execution of command {}: {}", command.getClass().getSimpleName(), e.getMessage());
    }

}
