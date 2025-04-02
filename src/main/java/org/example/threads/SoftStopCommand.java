package org.example.threads;

import lombok.RequiredArgsConstructor;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class SoftStopCommand implements Command {
    private final EventLoop eventLoop;

    @Override
    public void execute() {
        Runnable oldBehaviour = eventLoop::defaultBehaviour;

        // Меняем поведение на новое, которое будет останавливаться, если очередь пуста
        eventLoop.setBehaviour(() -> {
            if (!eventLoop.getQueue().isEmpty()) {
                oldBehaviour.run(); // Если очередь не пуста, выполняем старое поведение
            } else {
                eventLoop.stop(); // Если очередь пуста, останавливаем EventLoop
            }
        });
    }
}