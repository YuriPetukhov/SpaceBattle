package org.example.exceptions.type;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.AbstractExceptionHandling;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GeneralException extends AbstractExceptionHandling {


    public GeneralException(String className, String message) {
        super(className, message);
    }

    @Override
    protected void handleSpecificLogic() {
        super.logException();
    }
}
