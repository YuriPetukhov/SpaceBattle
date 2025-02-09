package org.example.exceptions.handler;

import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.LogException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogExceptionHandlerTest {

    @Mock
    private CommandQueue commandQueue;

    private LogExceptionHandler logExceptionHandler;

    @BeforeEach
    public void setUp() {
        logExceptionHandler = new LogExceptionHandler(commandQueue);
    }

    @Test
    public void shouldAddLogExceptionCommandToQueue() throws Exception {
        Command command = mock(Command.class);
        Exception exception = new RuntimeException("Test exception");

        logExceptionHandler.handle(command, exception);

        ArgumentCaptor<LogException> captor = ArgumentCaptor.forClass(LogException.class);
        verify(commandQueue).add(captor.capture());

        LogException capturedCommand = captor.getValue();
        assertNotNull(capturedCommand, "Captured LogExceptionCommand should not be null");

        assertEquals(command, capturedCommand.getCommand(), "Command should match");
        assertEquals(exception, capturedCommand.getException(), "Exception should match");
    }

    @Test
    public void shouldNotThrowExceptionWhenQueueIsFull() throws Exception {
        Command command = mock(Command.class);
        Exception exception = new RuntimeException("Test exception");

        doNothing().when(commandQueue).add(any(LogException.class));

        logExceptionHandler.handle(command, exception);

        verify(commandQueue).add(any(LogException.class));
    }
}
