package org.example.command;

import org.example.exceptions.type.NotEnoughFuelException;

@FunctionalInterface
public interface Command {
    void execute() throws Exception, NotEnoughFuelException;
}
