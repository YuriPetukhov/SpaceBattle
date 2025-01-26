package org.example.movement;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void testMove() {
        // Начальное положение
        Point initialLocation = new Point(12, 5);

        // Задаём скорость
        Velocity velocity = new Velocity(-7, 3);
        Vector vector = new Vector(velocity);

        // Создаём объект для перемещения
        MovingObjectImpl movingObject = new MovingObjectImpl(initialLocation, vector);

        // Выполняем перемещение
        Move move = new Move(movingObject);
        move.execute();

        // Проверяем новое положение
        Point newLocation = movingObject.getLocation();
        assertEquals(5, newLocation.getX());
        assertEquals(8, newLocation.getY());
    }

    @Test
    void testMoveThrowsExceptionWhenLocationIsUnreadable() {
        // Мок объекта с ошибкой при чтении положения
        MovingObjectImpl movingObject = new MovingObjectImpl(null, new Vector(new Velocity(1, 1)));

        Move move = new Move(movingObject);

        // Проверяем выброс исключения
        Exception exception = assertThrows(IllegalStateException.class, move::execute);
        assertEquals("Location is not set. Unable to get current location.", exception.getMessage());
    }

    @Test
    void testMoveThrowsExceptionWhenVelocityIsUnreadable() {
        // Мок объекта с ошибкой при чтении скорости
        MovingObjectImpl movingObject = new MovingObjectImpl(new Point(0, 0), null);

        Move move = new Move(movingObject);

        // Проверяем выброс исключения
        Exception exception = assertThrows(IllegalStateException.class, move::execute);
        assertEquals("Velocity is not set. Unable to get current velocity", exception.getMessage());
    }

    @Test
    void testMoveThrowsExceptionWhenLocationIsUnwritable() {
        // Создаём объект с начальным состоянием
        Point initialLocation = new Point(0, 0);
        Velocity velocity = new Velocity(1, 1);
        Vector vector = new Vector(velocity);

        // Мок с изменённым методом setLocation
        MovingObject movingObject = new MovingObjectImpl(initialLocation, vector) {
            @Override
            public void setLocation(Point newValue) {
                throw new IllegalStateException("Cannot set location");
            }
        };

        Move move = new Move(movingObject);

        // Проверяем выброс исключения
        Exception exception = assertThrows(IllegalStateException.class, move::execute);
        assertEquals("Cannot set location", exception.getMessage());
    }
}
