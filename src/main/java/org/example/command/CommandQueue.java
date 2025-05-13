package org.example.command;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.handler.ExceptionHandler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class CommandQueue {

    private final Queue<Command> queue = new ConcurrentLinkedQueue<>();
    private final ExceptionHandler exceptionHandler;
    private boolean isProcessing = false;

    public CommandQueue(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public synchronized void add(Command command) throws Exception {
        queue.offer(command);
        if (!isProcessing) {
            process();
        }
    }

    private void process() throws Exception {
        while (!queue.isEmpty()) {
            Command command;
            synchronized (this) {
                command = queue.poll();
            }

            try {
                assert command != null;
                command.execute();
            } catch (Exception e) {
                exceptionHandler.handle(command.getClass().getSimpleName(), e);
            }
        }
    }
}
