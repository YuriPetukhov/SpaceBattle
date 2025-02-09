package org.example.exceptions;

import org.example.command.Command;
import org.example.exceptions.type.GeneralException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class LogExceptionTest {

    @Mock
    private Command command;

    @Test
    void testGetExceptionType_ReturnsCorrectType() {
        Exception exception = new GeneralException("LogException", "exception");
        LogException commandToTest = new LogException(command, exception);

        String className = commandToTest.getClass().getSimpleName();
        String message = exception.getMessage();

        assertThat(className).isEqualTo("LogException");
        assertThat(message).isEqualTo("exception");
    }
}

