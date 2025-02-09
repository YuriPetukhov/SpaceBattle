package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.exceptions.type.InvalidVelocityException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Velocity {

    private int x;
    private int y;

    public void setX(int x) throws InvalidVelocityException {
        if (x < 0) throw new InvalidVelocityException(getClass().getSimpleName(), "X velocity cannot be negative");
        this.x = x;
    }

    public void setY(int y) throws InvalidVelocityException {
        if (y < 0) throw new InvalidVelocityException(getClass().getSimpleName(), "Y velocity cannot be negative");
        this.y = y;
    }
}

