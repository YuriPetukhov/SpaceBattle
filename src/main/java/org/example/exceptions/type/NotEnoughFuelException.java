package org.example.exceptions.type;

import org.example.command.Command;
import org.example.exceptions.AbstractExceptionHandling;
import org.springframework.stereotype.Component;

@Component
public class NotEnoughFuelException extends AbstractExceptionHandling {
    public NotEnoughFuelException(Command command, Exception exception) {
        super(command, exception);
        initCause(exception);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
