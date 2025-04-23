package org.example.movement;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Angle;
import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.springframework.context.annotation.Primary;

import java.util.HashSet;
import java.util.Set;

/**
 * Реализация MovingObject с поддержкой коллизий.
 */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Primary
public class MovingObjectImpl implements MovingObject {

    private Point location;
    private Vector velocity;
    private Angle angle;

    private final Set<MovingObject> neighbors = new HashSet<>();
    private boolean explosive;
    private boolean fragile;
    private boolean elastic;

    public MovingObjectImpl(Point location, Vector velocity, Angle angle) {
        this.location = location;
        this.velocity = velocity;
        this.angle = angle;
    }

    @Override
    public Point getLocation() throws LocationNotSetException {
        if (location == null) {
            throw new LocationNotSetException(getClass().getSimpleName(),
                    "Location is not set. Unable to get current location.");
        }
        log.debug("Getting current location: {}", location);
        return location;
    }

    @Override
    public Vector getVelocity() throws VelocityNotSetException {
        if (velocity == null) {
            throw new VelocityNotSetException(getClass().getSimpleName(),
                    "Velocity is not set. Unable to get current velocity");
        }
        log.debug("Getting current velocity: {}", velocity);
        return velocity;
    }

    @Override
    public void setLocation(Point newLocation) throws VelocityNotSetException {
        if (newLocation == null) {
            throw new VelocityNotSetException(getClass().getSimpleName(), "New location cannot be null");
        }
        log.debug("Setting new location: {}", newLocation);
        this.location = newLocation;
    }


    @Override
    public void addNeighbor(MovingObject other) {
        neighbors.add(other);
    }

    @Override
    public void clearNeighbors() {
        neighbors.clear();
    }

    /**
     * Имитация отскока от другого объекта.
     */
    @Override
    public void bounceFrom(MovingObject other) {
        log.info("Object {} bounces from {}", this, other);

    }

    /**
     * Логика уничтожения объекта.
     */
    @Override
    public void destroy() {
        log.info("Object {} is destroyed", this);

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
}
