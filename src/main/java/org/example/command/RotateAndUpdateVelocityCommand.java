package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.entity.Angle;
import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RotateAndUpdateVelocityCommand implements Command {
    private final Velocity velocity;
    private final Angle angle;
    private final ExceptionHandler exceptionHandler;

    @Override
    public void execute() throws Exception {
        try {
            double angleInRadians = (2 * Math.PI * angle.getD()) / angle.getN();

            int newX = (int) (velocity.getX() * Math.cos(angleInRadians) - velocity.getY() * Math.sin(angleInRadians));
            int newY = (int) (velocity.getX() * Math.sin(angleInRadians) + velocity.getY() * Math.cos(angleInRadians));
            velocity.setX(newX);
            velocity.setY(newY);
        } catch (Exception e) {
            exceptionHandler.handle(this, e);
        }
    }
}


