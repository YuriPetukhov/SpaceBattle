package org.example.mock;

import lombok.Getter;
import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.example.movement.MovingObject;

import java.util.HashSet;
import java.util.Set;

@Getter
public class MockMovingObject implements MovingObject {

    private Point location;
    private Vector velocity;

    private final Set<MovingObject> neighbors = new HashSet<>();
    private boolean destroyed = false;

    private boolean explosive = false;
    private boolean fragile = false;
    private boolean elastic = false;

    public MockMovingObject(Point location, Vector velocity) {
        this.location = location;
        this.velocity = velocity;
    }

    @Override
    public Point getLocation() throws LocationNotSetException {
        if (location == null) {
            throw new LocationNotSetException("Mock", "Location is null");
        }
        return location;
    }

    @Override
    public Vector getVelocity() throws VelocityNotSetException {
        if (velocity == null) {
            throw new VelocityNotSetException("Mock", "Velocity is null");
        }
        return velocity;
    }

    @Override
    public void setLocation(Point newValue) throws VelocityNotSetException {
        if (newValue == null) {
            throw new VelocityNotSetException("Mock", "Location cannot be null");
        }
        this.location = newValue;
    }

    @Override
    public void addNeighbor(MovingObject other) {
        neighbors.add(other);
    }

    @Override
    public void clearNeighbors() {
        neighbors.clear();
    }

    @Override
    public void bounceFrom(MovingObject other) {

    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean isExplosive() {
        return explosive;
    }

    @Override
    public boolean isFragile() {
        return fragile;
    }

    @Override
    public boolean isElastic() {
        return elastic;
    }

    // Сеттеры для тестов
    public void setExplosive(boolean explosive) {
        this.explosive = explosive;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public void setElastic(boolean elastic) {
        this.elastic = elastic;
    }

}
