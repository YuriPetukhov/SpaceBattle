package org.example.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class Retry implements Command {

    private final Command command;
    private final Exception e;

    @Override
    public void execute() throws Exception {
        try {
            log.info("Retrying command {} after exception: {}", command.getClass().getSimpleName(), e.getMessage());
            command.execute();
        } catch (Exception e) {
            log.error("Retry failed for command {}. Exception: {}", command.getClass().getSimpleName(), e.getMessage());
        }
    }
}
