package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Retry extends AbstractExceptionHandling {

    public Retry(Command command, Exception exception) {
        super(command, exception);
    }

    @Override
    protected void handleSpecificLogic() {
        try {
            log.info("Retrying command: {}", command.getClass().getSimpleName());
            command.execute();
        } catch (Exception e) {
            log.error("Retry failed for command: {}. Exception: {}", command.getClass().getSimpleName(), e.getMessage());
        }
    }
}
