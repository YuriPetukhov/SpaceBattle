package org.example.exceptions.handler;

import org.example.command.Command;

public interface CommandHandler {
    void handle(Command command, Exception e) throws Exception;
}
