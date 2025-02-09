package org.example.exceptions.strategy;
import org.example.command.Command;
import org.example.command.CommandQueue;
import org.example.exceptions.FailedRetry;
import org.example.exceptions.handler.LogExceptionHandler;
import org.example.exceptions.handler.RetryExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlingStrategyTest {

    @Mock
    private CommandQueue commandQueue;

    @Mock
    private RetryExceptionHandler retryExceptionHandler;

    @Mock
    private LogExceptionHandler logExceptionHandler;

    @InjectMocks
    private ExceptionHandlingStrategy exceptionHandlingStrategy;

    private Command mockCommand;
    private Exception mockException;

    @BeforeEach
    public void setup() {
        mockCommand = Mockito.mock(Command.class);
        mockException = new Exception("Test exception");
    }

    @Test
    public void shouldRetryTwoTimesBeforeLoggingException() throws Exception {
        exceptionHandlingStrategy.handle(mockCommand, mockException);
        exceptionHandlingStrategy.handle(mockCommand, mockException);

        verify(retryExceptionHandler, times(2)).handle(mockCommand, mockException);

        exceptionHandlingStrategy.handle(mockCommand, mockException);

        ArgumentCaptor<FailedRetry> captor = ArgumentCaptor.forClass(FailedRetry.class);
        verify(commandQueue).add(captor.capture());
        FailedRetry failedRetryCommand = captor.getValue();
        assert failedRetryCommand != null;
        assert failedRetryCommand.getCommand() == mockCommand;
        assert failedRetryCommand.getException() == mockException;

        verify(logExceptionHandler).handle(mockCommand, mockException);
        verify(retryExceptionHandler, times(2)).handle(mockCommand, mockException);
    }

    @Test
    public void shouldNotRetryAfterMaxRetries() throws Exception {
        exceptionHandlingStrategy.handle(mockCommand, mockException);
        exceptionHandlingStrategy.handle(mockCommand, mockException);
        exceptionHandlingStrategy.handle(mockCommand, mockException);

        verify(retryExceptionHandler, times(2)).handle(mockCommand, mockException);
        verify(logExceptionHandler).handle(mockCommand, mockException);
    }

    @Test
    public void shouldNotAddFailedRetryCommandIfNotMaxRetries() throws Exception {
        exceptionHandlingStrategy.handle(mockCommand, mockException);

        verify(retryExceptionHandler).handle(mockCommand, mockException);
        verify(commandQueue, never()).add(Mockito.any(FailedRetry.class));
        verify(logExceptionHandler, never()).handle(mockCommand, mockException);
    }
}
