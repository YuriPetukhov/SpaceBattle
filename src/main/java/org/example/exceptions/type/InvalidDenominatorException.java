package org.example.exceptions.type;

import org.example.exceptions.AbstractExceptionHandling;
import org.springframework.stereotype.Component;


public class InvalidDenominatorException extends AbstractExceptionHandling {
    public InvalidDenominatorException(String className, String message) {
        super(className, message);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
