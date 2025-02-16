package org.example.command;

import static org.mockito.Mockito.*;

import org.example.entity.Angle;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.example.exceptions.type.VelocityNotSetException;
import org.example.movement.Move;
import org.example.movement.MovingObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RotateAndUpdateVelocityCommandTest {
    private RotateAndUpdateVelocityCommand command;
    private MovingObject movingObject;
    private Velocity velocity;
    private Vector vector;
    private Angle angle;
    private ExceptionHandler exceptionHandler;
    private Move moveCommand;

    @BeforeEach
    void setUp() throws VelocityNotSetException {
        movingObject = mock(MovingObject.class);
        velocity = mock(Velocity.class);
        vector = mock(Vector.class);
        angle = mock(Angle.class);
        exceptionHandler = mock(ExceptionHandler.class);
        moveCommand = mock(Move.class);

        when(vector.getVelocity()).thenReturn(velocity);
        when(velocity.getX()).thenReturn(10);
        when(velocity.getY()).thenReturn(0);

        when(movingObject.getVelocity()).thenReturn(vector);

        command = new RotateAndUpdateVelocityCommand(movingObject, angle, moveCommand, exceptionHandler);
    }

    @Test
    void testExecute_NormalRotation() throws Exception {
        when(angle.getD()).thenReturn(1);
        when(angle.getN()).thenReturn(1);

        command.execute();

        verify(velocity).setX(10);
        verify(velocity).setY(0);

        verify(moveCommand).execute();
    }

    @Test
    void testExecute_RotationWithNegativeAngle() throws Exception {
        when(angle.getD()).thenReturn(1);
        when(angle.getN()).thenReturn(2);

        command.execute();

        verify(velocity).setX(-10);
        verify(velocity).setY(0);

        verify(moveCommand).execute();
    }

    @Test
    void testExecute_WhenExceptionOccurs() throws Exception {
        when(velocity.getX()).thenReturn(10);
        when(velocity.getY()).thenReturn(0);
        when(angle.getD()).thenReturn(1);
        when(angle.getN()).thenReturn(2);

        doThrow(new IllegalArgumentException("X velocity cannot be negative"))
                .when(velocity).setX(-10);

        command.execute();

        verify(exceptionHandler).handle(eq("RotateAndUpdateVelocityCommand"), any(IllegalArgumentException.class));
    }

    @Test
    void testExecute_WhenNullPointerExceptionOccurs() throws Exception {
        when(movingObject.getVelocity()).thenReturn(null);
        command.execute();
        verify(exceptionHandler).handle(eq("RotateAndUpdateVelocityCommand"), any(NullPointerException.class));
    }
}
