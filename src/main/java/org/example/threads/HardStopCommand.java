package org.example.threads;

import org.example.command.Command;

public class HardStopCommand implements Command {
    private final EventLoop eventLoop;

    public HardStopCommand(EventLoop eventLoop) {
        this.eventLoop = eventLoop;
    }

    @Override
    public void execute() {
        eventLoop.stop();
    }
}
