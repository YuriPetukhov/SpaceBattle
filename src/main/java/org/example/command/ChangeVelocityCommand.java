package org.example.command;

import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;


public class ChangeVelocityCommand implements Command {
    private final Velocity velocity;
    private final int deltaX;
    private final int deltaY;
    private final ExceptionHandler exceptionHandler;

    public ChangeVelocityCommand(Velocity velocity, int deltaX, int deltaY, ExceptionHandler exceptionHandler) {
        this.velocity = velocity;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void execute() throws Exception {
        try {
            velocity.setX(velocity.getX() + deltaX);
            velocity.setY(velocity.getY() + deltaY);
        } catch (Exception e) {
            exceptionHandler.handle(getClass().getSimpleName(), e);
        }
    }
}
