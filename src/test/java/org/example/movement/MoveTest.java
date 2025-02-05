package org.example.movement;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoveTest {

    @Test
    void testMove() throws Exception {
        Point initialLocation = new Point(12, 5);

        Velocity velocity = new Velocity(-7, 3);
        Vector vector = new Vector(velocity);

        MovingObjectImpl movingObject = new MovingObjectImpl(initialLocation, vector);

        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);

        Move move = new Move(movingObject, exceptionHandler);
        move.execute();

        Point newLocation = movingObject.getLocation();
        assertEquals(5, newLocation.getX());
        assertEquals(8, newLocation.getY());

        verifyNoInteractions(exceptionHandler);
    }

    @Test
    void testMoveThrowsExceptionWhenLocationIsUnreadable() throws Exception {
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);

        MovingObjectImpl movingObject = new MovingObjectImpl(null, new Vector(new Velocity(1, 1)));

        Move move = new Move(movingObject, exceptionHandler);

        try {
            move.execute();
            fail("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            verify(exceptionHandler, times(1)).handle(eq(move), eq(e));
        }
    }
    @Test
    void testMoveThrowsExceptionWhenVelocityIsUnreadable() throws Exception {
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        MovingObjectImpl movingObject = new MovingObjectImpl(new Point(0, 0), null);

        Move move = new Move(movingObject, exceptionHandler);

        Exception exception = assertThrows(IllegalStateException.class, move::execute);
        assertEquals("Velocity is not set. Unable to get current velocity", exception.getMessage());
        verify(exceptionHandler, times(1)).handle(eq(move), eq(exception));
    }

    @Test
    void testMoveThrowsExceptionWhenLocationIsUnwritable() {
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        Point initialLocation = new Point(0, 0);
        Velocity velocity = new Velocity(1, 1);
        Vector vector = new Vector(velocity);

        MovingObject movingObject = new MovingObjectImpl(initialLocation, vector) {
            @Override
            public void setLocation(Point newValue) {
                throw new IllegalStateException("Cannot set location");
            }
        };

        Move move = new Move(movingObject, exceptionHandler);

        Exception exception = assertThrows(IllegalStateException.class, move::execute);
        assertEquals("Cannot set location", exception.getMessage());
    }
}
