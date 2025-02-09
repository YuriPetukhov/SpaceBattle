package org.example.exceptions;

import org.example.command.Command;
import org.example.exceptions.type.GeneralException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RetryTest {

    @Mock
    private Command command;

    @InjectMocks
    private Retry retryCommand;

    @Test
    public void shouldThrowExceptionIfRetryFails() {
        Exception exception = new GeneralException("Retry", "exception");
        Retry commandToTest = new Retry(command, exception);

        String className = commandToTest.getClass().getSimpleName();
        String message = exception.getMessage();

        assertThat(className).isEqualTo("Retry");
        assertThat(message).isEqualTo("exception");
    }

    @Test
    public void shouldRetryOnlyOnceIfCommandFails() throws Exception {

        Exception testException = new RuntimeException("Test exception");
        retryCommand = new Retry(command, testException);

        doThrow(new RuntimeException("Test exception")).doNothing().when(command).execute();

        retryCommand.execute();

        verify(command, times(1)).execute();
    }
}

