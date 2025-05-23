package org.example.movement;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;

public interface MovingObject {

    Point getLocation() throws LocationNotSetException;

    Vector getVelocity() throws VelocityNotSetException;

    void setLocation(Point newValue) throws VelocityNotSetException;

    void addNeighbor(MovingObject other);

    void clearNeighbors();

    void bounceFrom(MovingObject other);

    void destroy();

    boolean isExplosive();
    boolean isFragile();
    boolean isElastic();
}
