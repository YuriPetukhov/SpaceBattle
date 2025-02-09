package org.example.rotation;

import org.example.entity.Angle;
import org.example.exceptions.handler.ExceptionHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RotateTest {

    @Test
    void testRotate() throws Exception {
        Angle initialAngle = new Angle(90, 360);
        Angle angularVelocity = new Angle(45, 360);
        RotatingObjectImpl rotatingObject = new RotatingObjectImpl(initialAngle, angularVelocity);

        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        Rotate rotate = new Rotate(rotatingObject, exceptionHandler);

        rotate.execute();

        assertEquals(new Angle(135, 360), rotatingObject.getAngle());

        verifyNoInteractions(exceptionHandler);
    }

    @Test
    void testRotateWithWrapAround() throws Exception {
        Angle initialAngle = new Angle(330, 360);
        Angle angularVelocity = new Angle(60, 360);
        RotatingObjectImpl rotatingObject = new RotatingObjectImpl(initialAngle, angularVelocity);

        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        Rotate rotate = new Rotate(rotatingObject, exceptionHandler);

        rotate.execute();

        assertEquals(new Angle(30, 360), rotatingObject.getAngle());

        verifyNoInteractions(exceptionHandler);
    }

    @Test
    void testRotateWithDifferentDenominatorsThrowsException() {
        Angle initialAngle = new Angle(90, 360);
        Angle angularVelocity = new Angle(1, 180);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            initialAngle.add(angularVelocity);
        });

        assertEquals("Angles must have the same denominator.", exception.getMessage());
    }

    @Test
    void testExecuteHandlesException() throws Exception {
        RotatingObject rotatingObject = mock(RotatingObject.class);
        doThrow(new IllegalStateException("Rotation error")).when(rotatingObject).rotate();

        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        Rotate rotate = new Rotate(rotatingObject, exceptionHandler);

        rotate.execute();

        verify(exceptionHandler, times(1)).handle(eq(rotate), any(IllegalStateException.class));
    }

    @Test
    void testRotateWithZeroVelocityDoesNotChangeAngle() throws Exception {
        Angle initialAngle = new Angle(90, 360);
        Angle zeroVelocity = new Angle(0, 360);
        RotatingObjectImpl rotatingObject = new RotatingObjectImpl(initialAngle, zeroVelocity);

        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        Rotate rotate = new Rotate(rotatingObject, exceptionHandler);

        rotate.execute();

        assertEquals(new Angle(90, 360), rotatingObject.getAngle());

        verifyNoInteractions(exceptionHandler);
    }

}
