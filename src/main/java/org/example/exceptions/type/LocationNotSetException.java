package org.example.exceptions.type;

import org.example.command.Command;
import org.example.exceptions.AbstractExceptionHandling;
import org.springframework.stereotype.Component;

public class LocationNotSetException extends AbstractExceptionHandling {


    public LocationNotSetException(String className, String message) {
        super(className, message);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
