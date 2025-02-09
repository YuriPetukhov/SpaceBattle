package org.example.exceptions.type;

import org.example.exceptions.AbstractExceptionHandling;
import org.springframework.stereotype.Component;

@Component
public class NotEnoughFuelException extends AbstractExceptionHandling {
    public NotEnoughFuelException(String className, String message) {
        super(className, message);
    }
    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }

}
