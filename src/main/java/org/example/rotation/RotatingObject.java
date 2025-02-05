package org.example.rotation;

import org.example.entity.Angle;

public interface RotatingObject {
    Angle getAngle();
    void setAngle(Angle angle);
    Angle getAngularVelocity();

    void rotate();
}
