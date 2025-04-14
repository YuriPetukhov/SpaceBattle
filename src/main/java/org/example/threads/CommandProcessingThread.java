package org.example.threads;

import java.util.concurrent.BlockingQueue;
import org.example.command.Command;

public class CommandProcessingThread extends Thread {
    private final BlockingQueue<Command> mainQueue;
    private final BlockingQueue<Command> targetQueue;
    private CommandProcessingState state = new NormalState();

    public CommandProcessingThread(BlockingQueue<Command> mainQueue,
                                   BlockingQueue<Command> targetQueue) {
        this.mainQueue = mainQueue;
        this.targetQueue = targetQueue;
    }

    @Override
    public void run() {
        try {
            while (state != null) {
                Command command = mainQueue.take();
                state = state.handle(command, mainQueue, targetQueue);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Поток завершил работу");
    }
}
