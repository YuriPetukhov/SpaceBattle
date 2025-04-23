package org.example.command;

import org.example.collision.NeighborhoodSystem;
import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.example.mock.MockMovingObject;
import org.example.movement.MovingObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UpdatePositionCommandTest {

    @Test
    void testObjectIsMovedAndCollisionsChecked() throws VelocityNotSetException, LocationNotSetException {
        MovingObject obj = mock(MovingObject.class);
        NeighborhoodSystem system = mock(NeighborhoodSystem.class);

        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);

        when(obj.getVelocity()).thenReturn(new Vector(new Velocity(5, 3)));
        when(obj.getLocation()).thenReturn(new Point(5, 7));

        UpdatePositionCommand command = new UpdatePositionCommand(obj, system, exceptionHandler);
        command.execute();

        verify(obj).clearNeighbors();
        verify(obj).setLocation(any());
        verify(system).updateObject(obj);
        verify(system).checkAllCollisions();
    }

    @Test
    void testObjectMovesCorrectly() throws LocationNotSetException {
        Point initialLocation = new Point(5, 5);
        Vector velocity = new Vector(new Velocity(5, 3));
        MockMovingObject obj = new MockMovingObject(initialLocation, velocity);
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        NeighborhoodSystem system = new NeighborhoodSystem(10, 2);
        UpdatePositionCommand command = new UpdatePositionCommand(obj, system, exceptionHandler);
        command.execute();
        assertEquals(new Point(10, 8), obj.getLocation());
    }

    @Test
    void testExceptionIsHandled() throws LocationNotSetException, VelocityNotSetException {
        MovingObject obj = mock(MovingObject.class);
        NeighborhoodSystem system = mock(NeighborhoodSystem.class);
        ExceptionHandler handler = mock(ExceptionHandler.class);

        when(obj.getLocation()).thenThrow(new LocationNotSetException("Test", "Location is null"));

        UpdatePositionCommand command = new UpdatePositionCommand(obj, system, handler);
        command.execute();

        verify(handler).handle(eq("UpdatePositionCommand"), any(LocationNotSetException.class));
    }


}