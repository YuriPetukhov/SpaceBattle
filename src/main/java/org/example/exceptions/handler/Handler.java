package org.example.exceptions.handler;

public interface Handler {

    void handle(String classname, Exception e) throws Exception;
}
