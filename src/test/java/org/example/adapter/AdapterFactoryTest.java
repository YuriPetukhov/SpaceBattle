package org.example.adapter;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.example.ioc.IoCContainer;
import org.example.movement.MovingObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class AdapterFactoryTest {

    @BeforeEach
    void setUp() {

        // Регистрация зависимостей для MovingObject
        IoCContainer.Resolve("IoC.Register", "org.example.movement.MovingObject:getLocation", (Function<Object[], Object>) args -> {
            IUObject obj = (IUObject) args[0];
            try {
                return obj.getProperty("location")
                        .orElseThrow(() -> new LocationNotSetException("MovingObjectAdapter", "Location is not set."));
            } catch (LocationNotSetException e) {
                throw new RuntimeException(e);
            }
        });

        IoCContainer.Resolve("IoC.Register", "org.example.movement.MovingObject:getVelocity", (Function<Object[], Object>) args -> {
            IUObject obj = (IUObject) args[0];
            try {
                Velocity velocity = (Velocity) obj.getProperty("velocity")
                        .orElseThrow(() -> new VelocityNotSetException("MovingObjectAdapter", "Velocity is not set."));
                return new Vector(velocity);
            } catch (VelocityNotSetException e) {
                throw new RuntimeException(e);
            }
        });

        IoCContainer.Resolve("IoC.Register", "org.example.movement.MovingObject:setLocation", (Function<Object[], Object>) args -> {
            IUObject obj = (IUObject) args[0];
            Point newValue = (Point) args[1];
            obj.setProperty("location", newValue);
            return null;
        });
    }

    @Test
    void testCreateAdapter() throws Exception {
        // Создаем объект UObject и устанавливаем свойства
        IUObject uObject = new UObject();
        Velocity velocity = new Velocity(-7, 3);
        Vector vector = new Vector(velocity);
        uObject.setProperty("location", new Point(10, 20));
        uObject.setProperty("velocity", velocity);

        // Создаем адаптер через IoC.Resolve
        MovingObject movingObject = (MovingObject) IoCContainer.Resolve("Adapter", MovingObject.class, uObject);

        // Проверяем, что адаптер корректно возвращает location
        Point location = movingObject.getLocation();
        assertEquals(new Point(10, 20), location);

        // Проверяем, что адаптер корректно возвращает velocity
        Vector velocity1 = movingObject.getVelocity();
        assertEquals(vector, velocity1);

        // Проверяем, что адаптер корректно устанавливает новое location
        movingObject.setLocation(new Point(30, 40));
        Point newLocation = movingObject.getLocation();
        assertEquals(new Point(30, 40), newLocation);
    }

    @Test
    void testLocationNotSetException() {
        // Создаем объект UObject без установленного location
        IUObject uObject = new UObject();

        // Создаем адаптер через IoC.Resolve
        MovingObject movingObject = (MovingObject) IoCContainer.Resolve("Adapter", MovingObject.class, uObject);

        // Проверяем, что вызов getLocation выбрасывает исключение
        RuntimeException exception = assertThrows(RuntimeException.class, movingObject::getLocation);

        // Проверяем, что сообщение об ошибке содержит нужный текст
        assertTrue(exception.getMessage().contains("Location is not set."), "Expected exception message to contain 'Location is not set.'");
    }

    @Test
    void testVelocityNotSetException() {
        // Создаем объект UObject без установленного velocity
        IUObject uObject = new UObject();

        // Создаем адаптер через IoC.Resolve
        MovingObject movingObject = (MovingObject) IoCContainer.Resolve("Adapter", MovingObject.class, uObject);

        // Проверяем, что вызов getVelocity выбрасывает исключение
        RuntimeException exception = assertThrows(RuntimeException.class, movingObject::getVelocity);

        // Проверяем, что сообщение об ошибке содержит нужный текст
        assertTrue(exception.getMessage().contains("Velocity is not set."), "Expected exception message to contain 'Velocity is not set.'");
    }
}