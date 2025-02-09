package org.example.exceptions.type;

import org.example.exceptions.AbstractExceptionHandling;
import org.springframework.stereotype.Component;

@Component
public class VelocityNotSetException extends AbstractExceptionHandling {
    public VelocityNotSetException(String className, String message) {
        super(className, message);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
