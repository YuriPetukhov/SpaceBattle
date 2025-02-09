package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FailedRetry extends AbstractExceptionHandling {

    public FailedRetry(Command command, Exception exception) {
        super(command, exception);
    }

    @Override
    protected void handleSpecificLogic() {
        log.error("Command failed twice and will not be retried: {}", command.getClass().getSimpleName());
    }
}

