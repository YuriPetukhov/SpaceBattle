package org.example.exceptions.handler;

import static org.junit.jupiter.api.Assertions.*;

import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.Retry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class RetryExceptionHandlerTest {

    @Mock
    private CommandQueue commandQueue;

    @Mock
    private Command command;

    private RetryExceptionHandler retryExceptionHandler;

    @BeforeEach
    public void setUp() {
        retryExceptionHandler = new RetryExceptionHandler(commandQueue);
    }

    @Test
    public void shouldHandleExceptionAndAddRetryCommandToQueue() throws Exception {
        Exception testException = new RuntimeException("Test exception");

        retryExceptionHandler.handleException(command, testException);

        ArgumentCaptor<Retry> captor = ArgumentCaptor.forClass(Retry.class);
        verify(commandQueue).add(captor.capture());

        Retry capturedCommand = captor.getValue();
        assertNotNull(capturedCommand);
        assertEquals(command, capturedCommand.getCommand());
        assertEquals(testException, capturedCommand.getException());
    }
}
