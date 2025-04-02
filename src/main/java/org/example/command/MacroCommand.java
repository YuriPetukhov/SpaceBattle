package org.example.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
public class MacroCommand implements Command {
    private final List<Command> commands;

    @Override
    public void execute() throws Exception {
        for (Command command : commands) {
            command.execute();
        }
    }
}
