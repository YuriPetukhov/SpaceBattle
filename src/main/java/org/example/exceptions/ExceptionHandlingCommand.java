package org.example.exceptions;

import org.example.command.Command;

public interface ExceptionHandlingCommand extends Command {
    Class<? extends Exception> getExceptionType();
}