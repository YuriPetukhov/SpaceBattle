package org.example.exceptions;

import org.example.command.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class LogExceptionCommandTest {

    @Mock
    private Command command;

    @Test
    void testGetExceptionType_ReturnsCorrectType() {
        Exception exception = new IllegalArgumentException("Test exception");
        LogException commandToTest = new LogException(command, exception);

        Class<? extends Exception> result = commandToTest.getExceptionType();

        assertThat(result).isEqualTo(IllegalArgumentException.class);
    }
}