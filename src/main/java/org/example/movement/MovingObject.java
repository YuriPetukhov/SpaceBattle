package org.example.movement;

import org.example.entity.Angle;
import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.exceptions.type.InvalidDenominatorException;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;

public interface MovingObject {

    Point getLocation() throws LocationNotSetException;

    Vector getVelocity() throws VelocityNotSetException;

    void setLocation(Point newValue) throws VelocityNotSetException;
}
