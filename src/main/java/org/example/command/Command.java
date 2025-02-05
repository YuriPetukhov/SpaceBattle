package org.example.command;

@FunctionalInterface
public interface Command {
    void execute() throws Exception;
}
