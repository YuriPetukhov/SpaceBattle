package org.example.command;

public class RunCommand implements Command {

    @Override
    public void execute() {
        System.out.println("RunCommand executed: переключение в NormalState.");
    }
}