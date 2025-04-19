package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.collision.NeighborhoodSystem;
import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.exceptions.handler.ExceptionHandler;
import org.example.movement.MovingObject;

@RequiredArgsConstructor
public class UpdatePositionCommand implements Command {
    private final MovingObject object;
    private final NeighborhoodSystem system;
    private final ExceptionHandler exceptionHandler;

    @Override
    public void execute() {
        object.clearNeighbors();
        try {
            Point currentLocation = object.getLocation();
            Vector velocity = object.getVelocity();
            int newX = currentLocation.getX() + velocity.getVelocity().getX();
            int newY = currentLocation.getY() + velocity.getVelocity().getY();
            object.setLocation(new Point(newX, newY));
        } catch (Exception e) {
            exceptionHandler.handle(getClass().getSimpleName(), e);
        }
        system.updateObject(object);
        system.checkAllCollisions();
    }

}

