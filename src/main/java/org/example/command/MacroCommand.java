package org.example.command;

import java.util.List;

public class MacroCommand implements Command {
    private final List<Command> commands;

    public MacroCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute() throws Exception {
        for (Command command : commands) {
            command.execute();
        }
    }
}
