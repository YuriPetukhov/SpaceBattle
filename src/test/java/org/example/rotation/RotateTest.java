package org.example.rotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotateTest {

    @Test
    void testRotate() {
        // Проверка корректного поворота
        RotatingObjectImpl rotatingObject = new RotatingObjectImpl(90, 45);
        Rotate rotate = new Rotate(rotatingObject);
        rotate.execute();

        // Проверяем новое направление
        assertEquals(135, rotatingObject.getDirection());
    }

    @Test
    void testGetDirectionThrowsExceptionWhenDirectionNotSet() {
        RotatingObjectImpl rotatingObject = new RotatingObjectImpl(null, 45);

        Exception exception = assertThrows(IllegalStateException.class, rotatingObject::getDirection);
        assertEquals("Direction is not set. Unable to get current direction.", exception.getMessage());
    }

    @Test
    void testSetDirectionThrowsExceptionForInvalidValues() {
        RotatingObjectImpl rotatingObject = new RotatingObjectImpl(90, 45);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> rotatingObject.setDirection(-1));
        assertEquals("Direction must be between 0 and 359 degrees.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> rotatingObject.setDirection(360));
        assertEquals("Direction must be between 0 and 359 degrees.", exception.getMessage());
    }

    @Test
    void testGetAngularVelocityThrowsExceptionForInvalidVelocity() {
        RotatingObjectImpl rotatingObject = new RotatingObjectImpl(90, 0);

        Exception exception = assertThrows(IllegalStateException.class, rotatingObject::getAngularVelocity);
        assertEquals("Angular velocity must be positive.", exception.getMessage());
    }

    @Test
    void testValidDirectionAndVelocity() {
        RotatingObjectImpl rotatingObject = new RotatingObjectImpl(90, 45);

        assertEquals(90, rotatingObject.getDirection());
        assertEquals(45, rotatingObject.getAngularVelocity());

        rotatingObject.setDirection(180);
        assertEquals(180, rotatingObject.getDirection());
    }
}
