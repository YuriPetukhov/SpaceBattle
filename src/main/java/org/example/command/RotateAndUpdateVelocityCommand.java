package org.example.command;

import org.example.entity.Angle;
import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.example.movement.Move;
import org.example.movement.MovingObject;

public class RotateAndUpdateVelocityCommand implements Command {
    private final MovingObject movingObject;
    private final Angle angle;
    private final Move moveCommand;
    private final ExceptionHandler exceptionHandler;

    public RotateAndUpdateVelocityCommand(MovingObject movingObject, Angle angle, Move moveCommand, ExceptionHandler exceptionHandler) {
        this.movingObject = movingObject;
        this.angle = angle;
        this.moveCommand = moveCommand;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void execute() throws Exception {
        try {
            double angleInRadians = (2 * Math.PI * angle.getD()) / angle.getN();

            Velocity currentVelocity = movingObject.getVelocity().getVelocity();

            int newX = (int) (currentVelocity.getX() * Math.cos(angleInRadians) - currentVelocity.getY() * Math.sin(angleInRadians));
            int newY = (int) (currentVelocity.getX() * Math.sin(angleInRadians) + currentVelocity.getY() * Math.cos(angleInRadians));

            currentVelocity.setX(newX);
            currentVelocity.setY(newY);

            moveCommand.execute();

        } catch (Exception e) {
            exceptionHandler.handle(getClass().getSimpleName(), e);
        }
    }
}


