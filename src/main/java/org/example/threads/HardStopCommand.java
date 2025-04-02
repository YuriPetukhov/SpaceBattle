package org.example.threads;

import lombok.RequiredArgsConstructor;
import org.example.command.Command;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class HardStopCommand implements Command {
    private final EventLoop eventLoop;

    @Override
    public void execute() {
        eventLoop.stop();
    }
}
