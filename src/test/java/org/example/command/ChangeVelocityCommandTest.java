package org.example.command;

import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChangeVelocityCommandTest {

    @Test
    void testExecuteChangesVelocity() throws Exception {
        Velocity velocity = new Velocity(0, 0);
        int deltaX = 5;
        int deltaY = 3;
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        ChangeVelocityCommand command = new ChangeVelocityCommand(velocity, deltaX, deltaY, exceptionHandler);

        command.execute();

        assertEquals(5, velocity.getX());
        assertEquals(3, velocity.getY());
        verify(exceptionHandler, never()).handle(any(), any());
    }

    @Test
    void testExecuteHandlesException() throws Exception {
        Velocity velocity = mock(Velocity.class);
        doThrow(new RuntimeException("Velocity cannot be negative")).when(velocity).setX(anyInt());
        int deltaX = -1;
        int deltaY = 3;
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        ChangeVelocityCommand command = new ChangeVelocityCommand(velocity, deltaX, deltaY, exceptionHandler);

        command.execute();

        verify(exceptionHandler, times(1)).handle(eq(command.getClass().getSimpleName()), any(RuntimeException.class));
    }



}