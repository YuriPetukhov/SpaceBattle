package org.example.movement;

import org.example.entity.Point;
import org.example.entity.Vector;

public interface MovingObject {

    Point getLocation();
    Vector getVelocity();
    void setLocation(Point newValue);
}
