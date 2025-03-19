package org.example.threads;

import lombok.RequiredArgsConstructor;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HandleExceptionCommand implements Command {
    private final Exception exception;

    @Override
    public void execute() {
        System.err.println("Ошибка при выполнении команды: " + exception.getMessage());
    }
}
