package org.example.rotation;

import org.example.entity.Angle;
import org.example.exceptions.type.InvalidDenominatorException;

public interface RotatingObject {
    Angle getAngle();
    void setAngle(Angle angle);
    Angle getAngularVelocity();

    void rotate() throws InvalidDenominatorException;
}
