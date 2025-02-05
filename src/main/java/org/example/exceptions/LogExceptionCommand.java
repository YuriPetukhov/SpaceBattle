package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogExceptionCommand extends AbstractExceptionHandlingCommand {

    public LogExceptionCommand(Command command, Exception exception) {
        super(command, exception);
    }

    @Override
    protected void handleSpecificLogic() {
        logException();
    }
}
