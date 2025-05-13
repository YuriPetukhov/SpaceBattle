package org.example.threads;

import org.example.command.Command;
import org.example.command.MoveToCommand;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
public class NormalState implements CommandProcessingState {

    @Override
    public CommandProcessingState handle(Command command,
                                         BlockingQueue<Command> mainQueue,
                                         BlockingQueue<Command> targetQueue) throws Exception {
        if (command instanceof HardStopCommand) {
            return null;
        } else if (command instanceof MoveToCommand) {
            return new MoveToState();
        }

        command.execute();
        return this;
    }
}
