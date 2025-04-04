package org.example.ioc;

import org.example.adapter.AdapterFactory;
import org.example.adapter.IUObject;
import org.example.entity.Point;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IoCContainer {

    private static final String IO_C_REGISTER = "IoC.Register";
    private static final String IO_C_REGISTER_COMMAND = "IoC.Register.Command";
    private static final String IO_C_REGISTER_SINGLETON = "IoC.Register.Singleton";
    private static final String SCOPES_NEW = "Scopes.New";
    private static final String SCOPES_CURRENT = "Scopes.Current";

    private static final Map<String, Function<Object[], Object>> handlers = new HashMap<>();
    private static final ThreadLocal<Map<String, Function<Object[], Object>>> scopes =
            ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<String> scopeIds = ThreadLocal.withInitial(() -> "global");

    static {
        // Регистрация стандартных обработчиков
        handlers.put(IO_C_REGISTER, args -> {
            if (args.length < 2 || !(args[1] instanceof Function)) {
                throw new IllegalArgumentException("Invalid registration: expected key and factory.");
            }
            String key = (String) args[0];
            Function<Object[], Object> factory = (Function<Object[], Object>) args[1];
            String scopeId = args.length > 2 ? (String) args[2] : null;

            if (scopeId != null) {
                scopes.get().put(key, factory);
            } else {
                handlers.put(key, factory);
            }
            return null;
        });

        handlers.put(IO_C_REGISTER_COMMAND, args -> {
            if (args.length < 2 || !(args[1] instanceof Function)) {
                throw new IllegalArgumentException("Invalid command registration: expected key and handler.");
            }
            String key = (String) args[0];
            Function<Object[], Object> handler = (Function<Object[], Object>) args[1];

            synchronized (handlers) {
                handlers.put(key, handler);
            }
            return null;
        });

        handlers.put(IO_C_REGISTER_SINGLETON, args -> {
            if (args.length < 2 || !(args[1] instanceof Function)) {
                throw new IllegalArgumentException("Invalid registration: expected key and factory.");
            }
            String key = (String) args[0];
            Function<Object[], Object> factory = (Function<Object[], Object>) args[1];
            Object instance = factory.apply(new Object[0]);
            synchronized (handlers) {
                handlers.put(key, (args1) -> instance);
            }
            return null;
        });

        handlers.put(SCOPES_NEW, args -> {
            if (args.length < 1 || !(args[0] instanceof String)) {
                throw new IllegalArgumentException("Invalid scope: expected scopeId.");
            }
            String scopeId = (String) args[0];
            scopes.set(new HashMap<>());
            scopeIds.set(scopeId);
            return null;
        });

        handlers.put(SCOPES_CURRENT, args -> scopeIds.get());

        // Регистрация фабрики для создания адаптеров
        handlers.put("Adapter", args -> {
            Class<?> iface = (Class<?>) args[0];
            Object obj = args[1];
            return AdapterFactory.createAdapter(iface, obj);
        });

        // Регистрация зависимостей для MovingObject
        registerMovingObjectDependencies();
    }

    private static void registerMovingObjectDependencies() {
        // Регистрация метода getLocation
        handlers.put("MovingObject:getLocation", args -> {
            IUObject obj = (IUObject) args[0];
            try {
                return obj.getProperty("location")
                        .orElseThrow(() -> new LocationNotSetException("MovingObjectAdapter", "Location is not set."));
            } catch (LocationNotSetException e) {
                throw new RuntimeException(e);
            }
        });

        // Регистрация метода getVelocity
        handlers.put("MovingObject:getVelocity", args -> {
            IUObject obj = (IUObject) args[0];
            try {
                return obj.getProperty("velocity")
                        .orElseThrow(() -> new VelocityNotSetException("MovingObjectAdapter", "Velocity is not set."));
            } catch (VelocityNotSetException e) {
                throw new RuntimeException(e);
            }
        });

        // Регистрация метода setLocation
        handlers.put("MovingObject:setLocation", args -> {
            IUObject obj = (IUObject) args[0];
            Point newValue = (Point) args[1];
            obj.setProperty("location", newValue);
            return null;
        });
    }

    public static Object Resolve(String key, Object... args) {
        if (handlers.containsKey(key)) {
            return handlers.get(key).apply(args);
        }

        Function<Object[], Object> factory = scopes.get().get(key);
        if (factory == null) {
            factory = handlers.get(key);
        }

        if (factory == null) {
            throw new RuntimeException("No dependency found for key: " + key);
        }

        return factory.apply(args);
    }
}