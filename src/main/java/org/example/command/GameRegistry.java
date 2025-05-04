package org.example.command;

import org.example.adapter.IUObject;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameRegistry {
    private final Map<String, IUObject> objects = new ConcurrentHashMap<>();

    public void register(String id, IUObject obj) {
        obj.setProperty("id", id);
        objects.put(id, obj);
    }

    public Optional<IUObject> get(String id) {
        return Optional.ofNullable(objects.get(id));
    }
}