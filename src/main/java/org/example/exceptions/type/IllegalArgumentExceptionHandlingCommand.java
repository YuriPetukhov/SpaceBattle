package org.example.exceptions.type;

import org.example.command.Command;
import org.example.exceptions.AbstractExceptionHandlingCommand;
import org.springframework.stereotype.Component;

@Component
public class IllegalArgumentExceptionHandlingCommand extends AbstractExceptionHandlingCommand {


    public IllegalArgumentExceptionHandlingCommand(Command command, Exception exception) {
        super(command, exception);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
