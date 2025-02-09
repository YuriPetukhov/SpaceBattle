package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeVelocityCommand implements Command {
    private final Velocity velocity;
    private final int deltaX;
    private final int deltaY;
    private final ExceptionHandler exceptionHandler;

    @Override
    public void execute() throws Exception {
        try {
            velocity.setX(velocity.getX() + deltaX);
            velocity.setY(velocity.getY() + deltaY);
        } catch (Exception e) {
            exceptionHandler.handle(this, e);
        }
    }
}
