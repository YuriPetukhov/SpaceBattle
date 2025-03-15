package org.example.adapter;

import java.util.Optional;

public interface IUObject {
    <T> void setProperty(String key, T value);
    <T> Optional<T> getProperty(String key);
}
