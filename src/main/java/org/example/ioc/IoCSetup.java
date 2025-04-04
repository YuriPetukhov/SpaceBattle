package org.example.ioc;

import org.example.entity.Point;
import org.example.adapter.IUObject;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.springframework.stereotype.Component;

import java.util.function.Function;

public class IoCSetup {
    public static void setup() {
        // Регистрация метода getLocation
        IoCContainer.Resolve("IoC.Register", "MovingObject:location.get", (Function<Object[], Object>) args -> {
            IUObject obj = (IUObject) args[0];
            try {
                return obj.getProperty("location").orElseThrow(() -> new LocationNotSetException("MovingObjectAdapter", "Location is not set."));
            } catch (LocationNotSetException e) {
                throw new RuntimeException(e);
            }
        });

        // Регистрация метода getVelocity
        IoCContainer.Resolve("IoC.Register", "MovingObject:velocity.get", (Function<Object[], Object>) args -> {
            IUObject obj = (IUObject) args[0];
            try {
                return obj.getProperty("velocity").orElseThrow(() -> new VelocityNotSetException("MovingObjectAdapter", "Velocity is not set."));
            } catch (VelocityNotSetException e) {
                throw new RuntimeException(e);
            }
        });

        // Регистрация метода setLocation
        IoCContainer.Resolve("IoC.Register", "MovingObject:location.set", (Function<Object[], Object>) args -> {
            IUObject obj = (IUObject) args[0];
            Point newValue = (Point) args[1];
            obj.setProperty("location", newValue);
            return null;
        });
    }
}