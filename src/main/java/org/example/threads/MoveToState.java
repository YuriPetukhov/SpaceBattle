package org.example.threads;

import org.example.command.Command;
import org.example.command.RunCommand;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
public class MoveToState implements CommandProcessingState {

    @Override
    public CommandProcessingState handle(Command command, BlockingQueue<Command> mainQueue,
                                         BlockingQueue<Command> targetQueue) {
        if (command instanceof HardStopCommand) {
            return null;
        } else if (command instanceof RunCommand) {
            return new NormalState();
        }

        targetQueue.add(command);
        System.out.println("Команда перенаправлена в другую очередь: " + command);
        return this;
    }
}
