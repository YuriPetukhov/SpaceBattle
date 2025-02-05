package org.example.exceptions;

import org.example.command.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RetryCommandTest {

    @Mock
    private Command command;

    @InjectMocks
    private RetryCommand retryCommand;

    @Test
    public void shouldThrowExceptionIfRetryFails() throws Exception {
        MockitoAnnotations.openMocks(this);
        doThrow(new RuntimeException("Test exception")).when(command).execute();

        assertThrows(RuntimeException.class, () -> {
            retryCommand.execute();
        });
    }

    @Test
    public void shouldRetryOnlyOnceIfCommandFails() throws Exception {

        Exception testException = new RuntimeException("Test exception");
        retryCommand = new RetryCommand(command, testException);

        doThrow(new RuntimeException("Test exception")).doNothing().when(command).execute();

        retryCommand.execute();

        verify(command, times(1)).execute();
    }
}

