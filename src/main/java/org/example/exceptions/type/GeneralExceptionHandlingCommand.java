package org.example.exceptions.type;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.exceptions.AbstractExceptionHandlingCommand;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GeneralExceptionHandlingCommand extends AbstractExceptionHandlingCommand {


    public GeneralExceptionHandlingCommand(Command command, Exception exception) {
        super(command, exception);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
