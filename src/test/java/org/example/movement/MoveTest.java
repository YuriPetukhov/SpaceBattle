package org.example.movement;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
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

            move.execute();

            verify(exceptionHandler, times(1)).handle(eq(move.getClass().getSimpleName()), any(LocationNotSetException.class));
        }

        @Test
        void testMoveThrowsExceptionWhenVelocityIsUnreadable() throws Exception {
            ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
            MovingObjectImpl movingObject = new MovingObjectImpl(new Point(0, 0), null);
            Move move = new Move(movingObject, exceptionHandler);

            move.execute();

            verify(exceptionHandler, times(1)).handle(eq(move.getClass().getSimpleName()), any(VelocityNotSetException.class));
        }

        @Test
        void testMoveThrowsExceptionWhenLocationIsUnwritable() throws Exception {
            ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
            MovingObject movingObject = mock(MovingObject.class);
            when(movingObject.getLocation()).thenReturn(new Point(0, 0));
            when(movingObject.getVelocity()).thenReturn(new Vector(new Velocity(1, 1)));
            doThrow(new IllegalStateException("Cannot set location")).when(movingObject).setLocation(any());

            Move move = new Move(movingObject, exceptionHandler);

            move.execute();

            verify(exceptionHandler, times(1)).handle(eq(move.getClass().getSimpleName()), any(IllegalStateException.class));
        }

}
