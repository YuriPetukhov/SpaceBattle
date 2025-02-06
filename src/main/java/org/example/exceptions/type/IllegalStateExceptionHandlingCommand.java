package org.example.exceptions.type;

import org.example.command.Command;
import org.example.exceptions.AbstractExceptionHandlingCommand;
import org.springframework.stereotype.Component;

@Component
public class IllegalStateExceptionHandlingCommand extends AbstractExceptionHandlingCommand {
    public IllegalStateExceptionHandlingCommand(Command command, Exception exception) {
        super(command, exception);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
