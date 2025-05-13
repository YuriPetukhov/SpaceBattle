package org.example.adapter;

import org.example.exceptions.type.CommandException;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public interface IUObject {
    <T> void setProperty(String key, T value);
    <T> Optional<T> getProperty(String key);

    default void executeCommand(String action, Map<String, Object> params) {
        Object raw = getProperty("cmd:" + action)
                .orElseThrow(() -> new CommandException("No handler for action " + action));
        @SuppressWarnings("unchecked")
        Consumer<Map<String, Object>> handler = (Consumer<Map<String, Object>>) raw;
        handler.accept(params);
    }
}
