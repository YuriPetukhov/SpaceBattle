package org.example.exceptions;

import org.example.command.Command;

public interface ExceptionHandling extends Command {
    Class<? extends Exception> getExceptionType();
}