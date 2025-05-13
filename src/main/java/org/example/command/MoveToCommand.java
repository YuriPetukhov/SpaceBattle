package org.example.command;


public class MoveToCommand implements Command {

    @Override
    public void execute() {
        System.out.println("MoveToCommand executed: переключение в режим MoveToState.");
    }
}