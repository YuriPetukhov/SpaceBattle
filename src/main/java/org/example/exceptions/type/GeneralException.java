package org.example.exceptions.type;

import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.exceptions.AbstractExceptionHandling;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GeneralException extends AbstractExceptionHandling {


    public GeneralException(Command command, Exception exception) {
        super(command, exception);
        initCause(exception);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
