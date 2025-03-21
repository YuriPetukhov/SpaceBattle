package org.example.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UObject implements IUObject {
    private final Map<String, Object> properties = new HashMap<>();

    @Override
    public <T> void setProperty(String key, T value) {
        properties.put(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getProperty(String key) {
        return Optional.ofNullable((T) properties.get(key));
    }
}

